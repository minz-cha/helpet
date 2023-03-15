var express = require('express');
var router = express.Router();
var db = require('../../db');

// 달력화면
// router.get('/', function (request, response) {
//     var title = '달력화면';
//     response.json({ "title": title })
// });

// 달력일정 등록
router.post('/add', (req, res) => {
    var date = req.body.date;
    var content = req.body.content;
    var userId = req.body.userId;
    var errorcode = true;

    db.query('INSERT INTO calendar (cal_idx, userId, date, content) VALUES(?,?,?,?)', [null, userId, date, content], function (error, data) {
        if (error) throw error;
        errorcode = false
        res.json({
            "error": errorcode,
            "message": "일정이 등록되었습니다."
        })
    })
})

// 달력일정 삭제
router.post('/delete', (req, res) => {
    var cal_idx = req.body.cal_idx;
    var errorcode = true;

    db.query('DELETE FROM calendar where cal_idx = ?', [cal_idx], function (error, data) {
        if (error) throw error;
        errorcode = false
        res.json({
            "error": errorcode,
            "cal_idx": cal_idx,
            "message": "일정이 삭제되었습니다."
        })
    });
})

//달력일정 수정
//이미 등록된 일정을 클릭 시
router.post('/', (req, res) => {
    var cal_idx = req.body.cal_idx;
    var content = req.body.content;
    var errorcode = true;

    db.query('UPDATE calendar SET content = ? where cal_idx = ?', [content, cal_idx], function (err, data) {
        if (error) throw err;
        errorcode = false;
        res.json({
            "error": errorcode,
            "modified content": content
        })
    })
});

module.exports = router;