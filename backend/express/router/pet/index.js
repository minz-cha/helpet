const petController = require("./mypet")
const petRouter = require("express").Router()
const multer = require("multer");
const path = require('path');
// const axios = require('axios');
const FormData = require('form-data');

// const formidable = require('formidable');
const fs = require('fs');

const storage = multer.diskStorage({
    destination: function (req, file, cb) {
        cb(null, 'uploads/');
    },
    filename: function (req, file, cb) {
        const ext = path.extname(file.originalname);
        const fileName = `${path.basename(
            file.originalname,
            ext
        )}_${Date.now()}${ext}`;
        cb(null, fileName);
    }
});

const limits = { fileSize: 5 * 1024 * 1024 };
const upload = multer({ storage, limits });

/**
 * @swagger
 * paths:
 *  /api/pet:
 *    post:
 *      summary: "반려동물 홈화면"
 *      description: "post방식: userId값 전송"
 *      tags: [Mypet]
 *      responses:
 *        "200":
 *          description: 유저의 등록된 반려동물 조회
 *          content:
 *            application/json:
 *              schema:
 *                type: object
 *                properties:
 *                    status:
 *                      type: string
 *                      example: "success"
 *                    results:
 *                      type: object
 *                      example:
 *                          [
 *                               {
 *                                   "petIdx": 1,
 *                                   "userId": "asdf",
 *                                   "petImg": "초코사진.jpg",
 *                                   "petSpecies": "강아지",
 *                                   "petName": "초코",
 *                                   "petAge": 3,
 *                                   "petBirth": "2020-03-05",
 *                                   "petGender": "여자"
 *                               },
 *                               {
 *                                   "petIdx": 2,
 *                                   "userId": "asdf",
 *                                   "petImg": "나비사진.jpg",
 *                                   "petSpecies": "고양이",
 *                                   "petName": "나비",
 *                                   "petAge": 5,
 *                                   "petBirth": "2018-09-26",
 *                                   "petGender": "여자"
 *                               },
 *                           ]
 */
petRouter.post('/', petController.petMain)

/**
 * @swagger
 * paths:
 *  /api/pet/register:
 *    post:
 *      summary: "반려동물 등록"
 *      description: "petImg, userId, petSpecies, petName, petAge, petBirth, petGender값 전송"
 *      tags: [Mypet]
 *      responses:
 *        "200":
 *          description: 유저의 반려동물 등록
 *          content:
 *            application/json:
 *              schema:
 *                type: object
 *                properties:
 *                    status:
 *                      type: string
 *                      example: "success"
 *                    userId:
 *                      type: string
 *                      example: "asdf"
 *                    petImg:
 *                      type: string
 *                      example: "초코사진.jpg"
 *                    petSpecies:
 *                      type: string
 *                      example: "강아지"
 *                    petName:
 *                      type: string
 *                      example: "초코"
 *                    petAge:
 *                      type: int
 *                      example: 3
 *                    petBirth:
 *                      type: string
 *                      example: "2023-06-01"
 *                    petGender:
 *                      type: string
 *                      example: "여자"
 */
petRouter.post('/register', upload.single('petImg'), petController.petRegister)

/**
 * @swagger
 * paths:
 *  /api/pet/delete:
 *    post:
 *      summary: "등록된 반려동물 삭제"
 *      description: "userId, petName값 전송"
 *      tags: [Mypet]
 *      responses:
 *        "200":
 *          description: 유저의 반려동물 삭제
 *          content:
 *            application/json:
 *              schema:
 *                type: object
 *                properties:
 *                    status:
 *                      type: string
 *                      example: "success"
 *                    userId:
 *                      type: string
 *                      example: "asdf"
 *                    petName:
 *                      type: string
 *                      example: "나비"
 */
petRouter.post('/delete', petController.petDelete)

/**
 * @swagger
 * paths:
 *  /api/pet/mypet-list:
 *    post:
 *      summary: "반려동물의 진단기록 조회"
 *      description: "userId, petName값 전송"
 *      tags: [Mypet]
 *      responses:
 *        "200":
 *          description: 해당 반려동물의 진단기록 조회
 *          content:
 *            application/json:
 *              schema:
 *                type: object
 *                properties:
 *                    status:
 *                      type: string
 *                      example: "success"
 *                    petName:
 *                      type: string
 *                      example: "초코"
 *                    petAge:
 *                      type: int
 *                      example: 3
 *                    petBirth:
 *                      type: string
 *                      example: "2023-06-01"
 *                    results:
 *                      type: object
 *                      example:
 *                          [
 *                               {
 *                                   "vectDate": "2023.06.01",
 *                                   "vectName": "결막염",
 *                                   "vectProb": 3.2
 *                               }
 *                           ]
 */
petRouter.post('/diag-list', petController.petList)

/**
 * @swagger
 * paths:
 *  /api/pet/list-save:
 *    post:
 *      summary: "진단기록 저장"
 *      description: "userId, petName, vectImg, vectDate, vectName, vectProb, vectContent값 전송"
 *      tags: [Mypet]
 *      responses:
 *        "200":
 *          description: 진단한 기록 저장
 *          content:
 *            application/json:
 *              schema:
 *                type: object
 *                properties:
 *                    status:
 *                      type: string
 *                      example: "success"
 */
petRouter.post('/list-save', upload.single('vectImg'), petController.petListSave)

module.exports = petRouter