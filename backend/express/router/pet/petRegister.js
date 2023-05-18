var express = require('express');
var router = express.Router();
var db = require('../../db');

db.connect();

// 반려동물 홈화면
router.post('/test', function (req, res) {
    var userId = req.body.userId;

    db.query('SELECT * FROM pet WHERE userId = ?', [userId], function (error, result) {
        if (error) throw error;
        res.json({
            status: "success",
            result: result
        })
        if (db.state === 'connected') {
            // 연결 종료
            db.end();
        }
    })
})

// 반려동물 등록
router.post('/register', (req, res) => {
    var userId = req.body.userId;
    var petImg = req.body.petImg;
    var petSpecies = req.body.petSpecies;
    var petName = req.body.petName;
    var petAge = req.body.petAge;
    var petBirth = req.body.petBirth;
    var petGender = req.body.petGender;

    db.query('INSERT INTO pet (petIdx, userId, petImg, petSpecies, petName, petAge, petBirth, petGender) VALUES (?,?,?,?,?,?,?,?)', [null, userId, petImg, petSpecies, petName, petAge, petBirth, petGender], function (error, data) {
        res.json({
            status: "success",
            userId: userId,
            petImg: petImg,
            petSpecies: petSpecies,
            petName: petName,
            petAge: petAge,
            petBirth: petBirth,
            petGender: petGender
        })
        if (db.state === 'connected') {
            // 연결 종료
            db.end();
        }
    })
})

// 반려동물 삭제
router.post('/delete', (req, res) => {
    var petIdx = req.body.petIdx;
    var userId = req.body.userId;
    // 등록할때 petIdx값을 클라이언트에 넘겨줘야할지 

    db.query('DELETE FROM pet WHERE petIdx = ?, userId = ?', [petIdx, userId], function (error, data) {
        if (error) throw error;
        res.json({
            status: "success",
            petIdx: petIdx,
            userId: userId,
            petName: petName,
        })
        if (db.state === 'connected') {
            // 연결 종료
            db.end();
        }
    })
})


module.exports = router;