var express = require('express');
var router = express.Router();
var db = require('../../db');
// var check = require('../../authCheck')

// 로그인 화면
exports.loginMain = (req, res) => {
    var title = '로그인화면';
    res.json({ "title": title })
}

// 로그인 프로세스
exports.login = (req, res) => {
    var userId = req.body.userId;
    var password = req.body.password;

    if (userId && password) {
        db.query('SELECT * FROM user WHERE userId = ?', [userId], function (error, results, fields) {
            if (error) {
                return res.status(500).json({ error: error.message });
            }
            if (results.length > 0) {       // db에서의 반환값이 있으면 로그인 성공
                if (password == results[0].password) { // 회원정보의 password와 입력한 password가 같은 경우
                    req.session.is_logined = true;
                    req.session.userId = userId;

                    res.status(200).json({
                        success: true,
                        userId: userId,
                        message: "로그인 성공"
                    })
                } else { // 입력한 비밀번호가 다를경우
                    res.json({
                        success: false,
                        userId: userId,
                        message: "비밀번호가 틀렸습니다."
                    })
                }
            } else { //입력한 아이디에 대한 정보가 없을때
                res.json({
                    success: false,
                    userId: userId,
                    message: "존재하지 않는 회원입니다."
                })
            }
        });
    } else {
        res.json({
            success: false,
            message: "아이디 및 비밀번호를 입력하세요."
        })
    }
}

// 로그아웃
exports.logout = (req, res) => {
    req.session.destroy(function (err) {
        res.json({ "result": "main으로 돌아갑니다" })
    });
}

// 아이디 중복 확인 프로세스
exports.idCheck = (req, res) => {
    var userId = req.body.userId;

    db.query('select * from user where userId = ?', [userId], function (error, results, fields) {
        if (error) {
            return res.status(500).json({ error: error.message });
        }

        if (results.length <= 0) {  //중복되는 아이디가 없음
            res.json({
                success: true,
                message: "사용가능한 아이디 입니다."
            })
        } else {           // DB에 같은 이름의 회원아이디가 있는 경우
            res.json({
                success: false,
                message: "이미 존재하는 아이디 입니다."
            })
        }
    });
}

//닉네임 중복 확인 프로세스
exports.nickNameCheck = (req, res) => {
    var nickname = req.body.nickname;

    db.query('select * from user where nickname = ?', [nickname], function (error, results, fields) {
        if (error) {
            return res.status(500).json({ error: error.message });
        }

        if (results.length <= 0) {  //중복되는 닉네임이 없음
            res.json({
                success: true,
                message: "사용가능한 닉네임 입니다."
            })
        } else {           // 중복되는 닉네임이 있는 경우
            res.json({
                success: false,
                message: "이미 존재하는 닉네임 입니다."
            })
        }
    });
}

// 회원가입 프로세스
exports.userRegister = (req, res) => {
    var userId = req.body.userId;
    var username = req.body.username;
    var phone = req.body.phone;
    var password = req.body.password;
    var nickname = req.body.nickname;

    if (username && phone && userId && password && nickname) {
        db.query('INSERT INTO user (userId, username, phone, password, nickname) VALUES(?,?,?,?,?)', [userId, username, phone, password, nickname], function (error, data) {
            if (error) {
                return res.status(500).json({ error: error.message });
            }
            res.status(200).json({
                success: true,
                userId: userId,
                password: password,
                message: "회원가입 성공"
            })
        })
    } else {        // 입력되지 않은 정보가 있는 경우
        res.json({
            success: false,
            message: "입력되지 않은 정보가 있습니다"
        })
    }
}
