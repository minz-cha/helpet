const authController = require("./auth")
const authRouter = require("express").Router()

// /**
//  * @swagger
//  * paths:
//  *  /api/auth/login:
//  *    get:
//  *      summary: "로그인 화면"
//  *      tags: [Auth]
//  *      responses:
//  *        "200":
//  *          content:
//  *            application/json:
//  *              schema:
//  *                type: object
//  *                properties:
//  *                    title:
//  *                      type: string
//  *                      example: "로그인화면"
//  */
authRouter.get('/login', authController.loginMain)

/**
 * @swagger
 * paths:
 *  /api/auth/login:
 *    post:
 *      summary: "로그인 진행"
 *      tags: [Auth]
 *      responses:
 *        "200":
 *          description: 로그인 성공
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
 *                    message:
 *                      type: string
 *                      example: "로그인 성공"
 */
authRouter.post('/login', authController.login)

// /**
//  * @swagger
//  * paths:
//  *  /api/auth/logout:
//  *    get:
//  *      summary: "로그아웃 화면"
//  *      tags: [Auth]
//  *      responses:
//  *        "200":
//  *          content:
//  *            application/json:
//  *              schema:
//  *                type: object
//  *                properties:
//  *                    result:
//  *                      type: string
//  *                      example: "main으로 돌아갑니다"
//  */
authRouter.get('/logout', authController.logout)

/**
 * @swagger
 * paths:
 *  /api/auth/id-check:
 *    post:
 *      summary: "아이디 중복 확인"
 *      tags: [Auth]
 *      responses:
 *        "200":
 *          description: 사용가능한 아이디임을 확인
 *          content:
 *            application/json:
 *              schema:
 *                type: object
 *                properties:
 *                    success:
 *                      type: string
 *                      example: "true"
 *                    message:
 *                      type: string
 *                      example: "사용가능한 아이디 입니다."
 *        "409":
 *          description: 중복된 아이디임을 확인
 *          content:
 *            application/json:
 *              schema:
 *                type: object
 *                properties:
 *                    success:
 *                      type: string
 *                      example: "false"
 *                    message:
 *                      type: string
 *                      example: "이미 존재하는 아이디 입니다."
 */
authRouter.post('/id-check', authController.idCheck)

/**
 * @swagger
 * paths:
 *  /api/auth/nickname-check:
 *    post:
 *      summary: "닉네임 중복 확인"
 *      tags: [Auth]
 *      responses:
 *        "200":
 *          description: 사용가능한 닉네임임을 확인
 *          content:
 *            application/json:
 *              schema:
 *                type: object
 *                properties:
 *                    success:
 *                      type: string
 *                      example: "true"
 *                    message:
 *                      type: string
 *                      example: "사용가능한 닉네임 입니다."
 *        "409":
 *          description: 중복된 닉네임임을 확인
 *          content:
 *            application/json:
 *              schema:
 *                type: object
 *                properties:
 *                    success:
 *                      type: string
 *                      example: "false"
 *                    message:
 *                      type: string
 *                      example: "이미 존재하는 닉네임 입니다."
 */
authRouter.post('/nickname-check', authController.nickNameCheck)

/**
 * @swagger
 * paths:
 *  /api/auth/register:
 *    post:
 *      summary: "회원가입 진행"
 *      tags: [Auth]
 *      responses:
 *        "200":
 *          description: 회원가입 성공
 *          content:
 *            application/json:
 *              schema:
 *                type: object
 *                properties:
 *                    success:
 *                      type: string
 *                      example: "true"
 *                    userId:
 *                      type: string
 *                      example: "asdf"
 *                    password:
 *                      type: string
 *                      example: "1234"
 *                    message:
 *                      type: string
 *                      example: "회원가입 성공"
 *        "405":
 *          description: 입력되지 않은 값으로 시도한 경우
 *          content:
 *            application/json:
 *              schema:
 *                type: object
 *                properties:
 *                    success:
 *                      type: string
 *                      example: "false"
 *                    message:
 *                      type: string
 *                      example: "입력되지 않은 정보가 있습니다"
 */
authRouter.post('/register', authController.userRegister)

module.exports = authRouter