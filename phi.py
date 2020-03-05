import cv2
from mtcnn.mtcnn import MTCNN
import sys
import json

def getRatio(h, w):
	'''return ratios'''
	ratio = h/w
	score = (ratio/1.618)*100
	return score

def getVerticalPts(keypoints):
	'''y-axis ratios(2)'''
	x1, y1 = keypoints['left_eye']
	x2, y2 = keypoints['right_eye']
	# approximation for line
	v1 = int((y1+y2)/2)

	x2, v2 = keypoints['nose']

	x1, y1 = keypoints['mouth_left']
	x2, y2 = keypoints['mouth_right']
	#approx line
	v3 = int((y1+y2)/2)

	return v1, v2, v3

def getHorizontalPts(keypoints):
	'''x-axis ratios(2)'''
	h2, y = keypoints['left_eye']
	h3, y = keypoints['right_eye']
	return h2, h3

pic_name = sys.argv[1] 
detector = MTCNN()
image = cv2.imread(pic_name)

result = detector.detect_faces(image)
#print(result)

x,y,w,h = result[0]['box']
keypoints = result[0]['keypoints']

#cv2.rectangle(image, (x, y), (x+w, y+h), (0,0,0), 2)
#cv2.circle(image,(keypoints['left_eye']), 2, (0,0,255), 2)
#cv2.circle(image,(keypoints['right_eye']), 2, (0,0,255), 2)
#cv2.circle(image,(keypoints['nose']), 2, (0,0,255), 2)
#cv2.circle(image,(keypoints['mouth_left']), 2, (0,0,255), 2)
#cv2.circle(image,(keypoints['mouth_right']), 2, (0,0,255), 2)

ratio1 = getRatio(h, w)

#vertical ratios
v1, v2, v3 = getVerticalPts(keypoints)
v4 = y+h
ratio2 = getRatio(v3-v1, v2-v1)
ratio3 = getRatio(v4-v1, v3-v1)

#horizontal ratios
h1 = x
h4 = x+w
h2, h3 = getHorizontalPts(keypoints)
ratio4 = getRatio(h3-h1, h3-h2)
ratio5 = getRatio(h4-h1, h3-h1)

#print(f"R1 : {ratio1}")
#cv2.rectangle(image, (h1, v1), (h4, v3), (0,0,0), 2)
#cv2.rectangle(image, (h1, v3), (h4, v4), (0,0,0), 2)
#cv2.rectangle(image, (h1, v1), (h4, v3), (0,0,0), 2)
#cv2.rectangle(image, (h1, v1), (h4, v2), (0,0,0), 2)
#print(f"R2 : {ratio2}")
#print(f"R3 : {ratio3}")
#cv2.rectangle(image, (h1, y), (h3, v4), (0,0,0), 2)
#cv2.rectangle(image, (h2, y), (h3, v4), (0,0,0), 2)
#cv2.rectangle(image, (h1, y), (h4, v4), (0,0,0), 2)
#cv2.rectangle(image, (h1, y), (h3, v4), (0,0,0), 2)
#print(f"R4 : {ratio4}")
#print(f"R5 : {ratio5}")
scores = {
	'score1':ratio1,
	'score2':ratio2,
	'score3':ratio3,
	'score4':ratio4,
	'score5':ratio5
}

with open('scores.txt','w') as out:
	json.dump(scores, out)
#cv2.imwrite("output.png",image)
