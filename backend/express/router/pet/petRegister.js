var express = require('express');
var router = express.Router();
var db = require('../../db');

// 반려동물 등록 홈화면
router.get('/:userId', function (req, res) {
    var userId = req.body.userId;

    db.connect();
    db.query('SELECT petImg, petName, petAge, petBirth, petGender WHERE userId = ?', [userId], function (error, result) {
        if (error) throw err;
        res.json({
            status: "success",
            petList: result
        })
    })
    db.end();
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

    db.connect();
    db.query('INSERT INTO pet (petIdx, userId, petImg, petSpecies, petName, petAge, petBirth, petGender) VALUES (?,?,?,?,?,?,?,?)', [null, userId, petImg, petSpecies, petName, petAge, , petGender], function (error, data) {
        if (error) throw error;
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
    })
    db.end();
})

module.exports = router;