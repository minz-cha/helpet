const calendarController = require("./calendar")
const calendarRouter = require("express").Router()

/**
 * @swagger
 * paths:
 *  /api/calendar:
 *    post:
 *      summary: "캘린더접속시 당일 날짜를 기준으로 월별 데이터 조회"
 *      description: "post방식: userId값 전송"
 *      tags: [Calendar]
 *      responses:
 *        "200":
 *          description: 당일 날짜 기준 월별 데이터 조회
 *          content:
 *            application/json:
 *              schema:
 *                type: object
 *                properties:
 *                    status:
 *                      type: string
 *                      example: "success"
 *                    month:
 *                      type: int
 *                      example: 6
 *                    userId:
 *                      type: string
 *                      example: "asdf"
 *                    results:
 *                      type: object
 *                      example:
 *                          [
 *                               {
 *                                   "cal_idx": 1,
 *                                   "userId": "asdf",
 *                                   "date": "2023.06.01",
 *                                   "month": 6,
 *                                   "title": "test",
 *                                  "description": "test content"
 *                               },
 *                               {
 *                                   "cal_idx": 2,
 *                                   "userId": "asdf",
 *                                   "date": "2023.06.02",
 *                                   "month": 6,
 *                                   "title": "test2",
 *                                  "description": "test content2"
 *                               },
 *                           ]
 */
calendarRouter.post('/', calendarController.calendarMain)

calendarRouter.post('/month', calendarController.calendarMonth)

/**
 * @swagger
 * paths:
 *  /api/calendar/add:
 *    post:
 *      summary: "일정 등록"
 *      tags: [Calendar]
 *      responses:
 *        "200":
 *          description: 일정 등록 성공
 *          content:
 *            application/json:
 *              schema:
 *                type: object
 *                properties:
 *                    status:
 *                      type: string
 *                      example: "success"
 *                    message:
 *                      type: string
 *                      example: "일정이 등록되었습니다."
 */
calendarRouter.post('/add', calendarController.calendarAdd)

/**
 * @swagger
 * paths:
 *  /api/calendar/update:
 *    post:
 *      summary: "일정 수정"
 *      tags: [Calendar]
 *      responses:
 *        "200":
 *          description: 일정 수정 성공
 *          content:
 *            application/json:
 *              schema:
 *                type: object
 *                properties:
 *                    status:
 *                      type: string
 *                      example: "success"
 *                    title:
 *                      type: string
 *                      example: "수정된 제목"
 *                    description:
 *                      type: string
 *                      example: "수정된 내용"
 */
calendarRouter.put('/update', calendarController.calendarUpdate)

/**
 * @swagger
 * paths:
 *  /api/calendar/delete:
 *    post:
 *      summary: "일정 삭제"
 *      tags: [Calendar]
 *      responses:
 *        "200":
 *          description: 일정 삭제 성공
 *          content:
 *            application/json:
 *              schema:
 *                type: object
 *                properties:
 *                    status:
 *                      type: string
 *                      example: "success"
 *                    cal_idx:
 *                      type: int
 *                      example: 3
 *                    message:
 *                      type: string
 *                      example: "일정이 삭제되었습니다."
 */
calendarRouter.delete('/delete/:cal_idx', calendarController.calendarDelete)

module.exports = calendarRouter