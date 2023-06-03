const express = require('express')
const session = require('express-session')
const bodyParser = require('body-parser');

const router = require("express").Router()

const FileStore = require('session-file-store')(session)
const app = express()

var authRouter = require('../lib_login/auth');
var authCheck = require('../lib_login/authCheck.js');
// var calendarRouter = require('../calendar/index');
var calendar = require("../calendar");
var communityRouter = require('../community/community');
var petRouter = require('../pet/mypet');

app.use('/static', express.static('static'))
app.use(express.urlencoded({
    extended: true
}))
app.use(express.json());
const port = 3000


app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: false }));
app.use(session({
    secret: 'secret test',   // 원하는 문자 입력
    resave: false,
    saveUninitialized: true,
    store: new FileStore(),
}))

// swagger 문서화
const { swaggerUi, specs } = require("../swagger/swagger");
app.use("/api-docs", swaggerUi.serve, swaggerUi.setup(specs))

// 세션
app.get('/api/*', function (req, res, next) {
    if (req.session.userId == undefined) res.status(500).send('Something broke!');
    else next();
});

/**
 * @swagger
 * tags:
 *   name: auth
 *   description: 유저 추가 수정 삭제 조회
 */

// 인증 라우터
app.use('/api/auth', authRouter);

// // 캘린더 라우터
app.use('/api/calendar', calendar);
// router.calendar('api/calendar', calendar)

// 커뮤니티 라우터
app.use('/api/community', communityRouter);

// 반려동물 라우터
app.use('/api/pet', petRouter);

// 메인 페이지
app.get('/main', (req, res) => {
    if (!authCheck.isOwner(req, res)) {  // 로그인 안되어있으면 로그인 페이지로 이동시킴
        res.json({ "result": "Login page로 이동" })
    }
    res.json({ "result": "Login Success! 메인화면입니다" })
})

app.listen(port, () => {
    console.log(`Example app listening on port ${port}`)
})

