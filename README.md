# Ov5

This is a cpp code runner
## Prerequisites:
node.js and npm  
docker  
java
## Installation:
```bash
git clone --recursive https://github.com/robvold/ov5.git
```

Server - in a terminal:
```bash
cd ov5/ov5server
docker build -t ov .
java Server
```
Client - in a new terminal:
```bash
cd ov5/ov5frontend
npm install
npm start
```
Your app is now running on localhost:3000
