const express = require('express')
const session = require('express-session')
const bodyParser = require('body-parser');

// mysql 연결하는 코드를 FileStore 대신하는 코드 쓸 수 있음 
const FileStore = require('session-file-store')(session)
const app = express()

var authRouter = require('../lib_login/auth');
var authCheck = require('../lib_login/authCheck.js');
var calendarRouter = require('../calendar/calendar');
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

// 세션
app.get('/api/*', function (req, res, next) {
    if (req.session.userId == undefined) res.status(500).send('Something broke!');
    else next();
});

// 인증 라우터
app.use('/api/auth', authRouter);

// 캘린더 라우터
app.use('/api/calendar', calendarRouter);

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