const dayjs = require("dayjs");
var express = require('express');
var router = express.Router();
var db = require('../../db');

// db.connect();

// 달력화면 접속 -> 해당 날짜(디폴트값)의 일정이 뜨게됨
exports.calendarMain = (req, res) => {
    var title = '달력화면';
    var now = dayjs().format("YYYY.MM.DD");
    var month = parseInt(now.slice(5, 7))
    var userId = req.body.userId;

    db.query('SELECT cal_idx, date, title, description FROM calendar where month = ? and userId = ?', [month, userId], function (error, result) {
        if (error) {
            return res.status(500).json({ error: error.message });
        }
        res.json({
            status: "success",
            month: month,
            userId: userId,
            result: result
        })
        // if (db.state === 'connected') {
        //     // 연결 종료
        //     db.end();
        // }
    })
}

// 달력화면 접속 -> 해당 날짜(디폴트값)의 일정이 뜨게됨
exports.calendarMonth = (req, res) => {
    var month = req.body.month;
    var userId = req.body.userId;

    db.query('SELECT cal_idx, date, title, description FROM calendar where month = ? and userId = ?', [month, userId], function (error, result) {
        if (error) {
            return res.status(500).json({ error: error.message });
        }
        res.json({
            status: "success",
            month: month,
            userId: userId,
            result: result
        })
        // if (db.state === 'connected') {
        //     // 연결 종료
        //     db.end();
        // }
    })
}

// 달력일정 등록
exports.calendarAdd = (req, res) => {
    var date = req.body.date;
    var title = req.body.title;
    var description = req.body.description;
    var userId = req.body.userId;
    var month = parseInt(date.slice(5, 7))

    db.query('INSERT INTO calendar (cal_idx, userId, date, month, title, description) VALUES(?,?,?,?,?,?)', [null, userId, date, month, title, description], function (error, data) {
        if (error) {
            return res.status(500).json({ error: error.message });
        }
        res.json({
            status: "success",
            message: "일정이 등록되었습니다."
        })
        // if (db.state === 'connected') {
        //     // 연결 종료
        //     db.end();
        // }
    })
}

// 달력일정 삭제
exports.calendarDelete = (req, res) => {
    var cal_idx = req.body.cal_idx;

    db.query('DELETE FROM calendar where cal_idx = ?', [cal_idx], function (error, data) {
        if (error) {
            return res.status(500).json({ error: error.message });
        }
        res.json({
            success: true,
            cal_idx: cal_idx,
            message: "일정이 삭제되었습니다."
        })
        if (db.state === 'connected') {
            // 연결 종료
            db.end();
        }
    });
}

//달력일정 수정
//이미 등록된 일정을 클릭 시
exports.calendarUpdate = (req, res) => {
    var userId = req.body.userId;
    var date = req.body.date;
    var title = req.body.title;
    var description = req.body.description;

    db.query('UPDATE calendar SET title = ?, description = ? where date = ? and userId = ?', [title, description, date, userId], function (error, data) {
        if (error) {
            return res.status(500).json({ error: error.message });
        }
        res.json({
            success: true,
            title: title,
            description: description
        })
        // if (db.state === 'connected') {
        //     // 연결 종료
        //     db.end();
        // }
    })
}