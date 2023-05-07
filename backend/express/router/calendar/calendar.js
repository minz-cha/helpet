const dayjs = require("dayjs");
var express = require('express');
var router = express.Router();
var db = require('../../db');

// 달력화면 접속 -> 해당 날짜(디폴트값)의 일정이 뜨게됨
router.get('/', function (req, res) {
    var title = '달력화면';
    var now = dayjs();

    db.connect();
    db.query('SELECT cal_idx, title FROM calendar WHERE date = ? ', [now.format("YYYY.MM.DD")], function (error, result) {
        if (error) throw error;
        res.json({
            success: true,
            today: now.format("YYYY.MM.DD"),
            content: result
        })
    })
    db.end();
});

// 달력일정 등록
router.post('/add', (req, res) => {
    var date = req.body.date;
    var title = req.body.title;
    var content = req.body.content;
    var userId = req.body.userId;
    db.connect();
    db.query('INSERT INTO calendar (cal_idx, userId, date, title, content) VALUES(?,?,?,?,?)', [null, userId, date, title, content], function (error, data) {
        if (error) throw error;
        res.json({
            success: true,
            message: "일정이 등록되었습니다."
        })
    })
    db.end()
})

// 달력일정 삭제
router.post('/delete', (req, res) => {
    var cal_idx = req.body.cal_idx;

    db.connect();
    db.query('DELETE FROM calendar where cal_idx = ?', [cal_idx], function (error, data) {
        if (error) throw error;
        res.json({
            success: true,
            cal_idx: cal_idx,
            message: "일정이 삭제되었습니다."
        })
    });
    db.end();
})

//달력일정 수정
//이미 등록된 일정을 클릭 시
router.post('/update', (req, res) => {
    var cal_idx = req.body.cal_idx;
    var title = req.body.title;
    var content = req.body.content;

    db.connect();
    db.query('UPDATE calendar SET title = ?, content = ? where cal_idx = ?', [title, content, cal_idx], function (err, data) {
        if (error) throw err;
        res.json({
            success: true,
            title: title,
            content: content
        })
    })
    db.end();
});

module.exports = router;