var express = require('express');
var router = express.Router();
var db = require('../../db');

//커뮤니티 접속 시 전체게시판이 먼저 나타나게 됨 (default)
router.get('/', function (req, res) {
    var page = '커뮤니티';

    db.connect();
    db.query('SELECT com_idx, title, content FROM community WHERE category = 0', function (error, result) {
        if (error) throw error;
        res.json({
            success: true,
            page: page,
            list: result //com_idx, title, content 조회
        })
    })
    db.end();
})

//공유: 1, Q&A: 2, 내새끼: 3
//api 주소 변경 필요
router.get('/', function (req, res) {
    var category = req.body.category; //integer

    db.connect();
    db.query('SELECT com_idx, title, content FROM community WHERE category = ?', [category], function (error, result) {
        if (error) throw error;
        errorcode = false
        res.json({
            success: true,
            page: page,
            list: result //com_idx, title, content 조회
        })
    })
    db.end();
})

module.exports = router;