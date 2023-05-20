var express = require('express');
var router = express.Router();
var db = require('../../db');

// 반려동물 홈화면
router.post('/', function (req, res) {
    var userId = req.body.userId;

    db.query('SELECT * FROM pet WHERE userId = ?', [userId], function (error, result) {
        if (error) {
            return res.status(500).json({ error: error.message });
        }
        res.json({
            status: "success",
            result: result
        })
        if (db.state === 'connected') {
            // 연결 종료
            // db.end();
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
        if (error) {
            return res.status(500).json({ error: error.message });
        }
        res.json({
            status: "success",
        })
        if (db.state === 'connected') {
            // 연결 종료
            // db.end();
        }
    })
})

// 반려동물 삭제
router.post('/delete', (req, res) => {
    var petIdx = req.body.petIdx;
    var userId = req.body.userId;
    // 등록할때 petIdx값을 클라이언트에 넘겨줘야할지 

    db.query('DELETE FROM pet WHERE petIdx = ?, userId = ?', [petIdx, userId], function (error, data) {
        if (error) {
            return res.status(500).json({ error: error.message });
        }
        res.json({
            status: "success",
            petIdx: petIdx,
            userId: userId,
            petName: petName,
        })
        if (db.state === 'connected') {
            // 연결 종료
            // db.end();
        }
    })
})

//진단결과 저장
router.post('/list-save', function (req, res) {
    var userId = req.body.userId;
    var petName = req.body.petName;
    var vectImg = req.body.vectImg;
    var vectDate = req.body.vectDate;
    var vectName = req.body.vectName;
    var vectProb = req.body.vectProb;
    var vectContent = req.body.vectContent;

    db.query('select petIdx from pet where userId = ? and petName = ?', [userId, petName], function (error, result) {
        if (error) {
            return res.status(500).json({ error: error.message });
        }
        if (result.length === 0) {
            return res.status(404).json({ message: "No results found." });
        }
        const petIdx = result[0].petIdx;

        db.query('INSERT INTO diag_pet(diagIdx, petIdx, vectImg, vectDate, vectName, vectProb, vectContent) VALUES (?,?,?,?,?,?,?)', [null, petIdx, vectImg, vectDate, vectName, vectProb, vectContent], function (error, result) {
            if (error) {
                return res.status(500).json({ error: error.message });
            }
            res.json({
                status: "success"
            })
        })
    })
})

//진단결과 리스트 조회 (진단리스트 홈화면)
router.post('/mypet-list', function (req, res) {
    var userId = req.body.userId;
    var petName = req.body.petName;

    db.query('select pet.petName, pet.petAge, pet.petBirth, diag_pet.vectDate, diag_pet.vectName, diag_pet.vectProb from pet join diag_pet on pet.petIdx = diag_pet.petIdx where pet.userId = ? and pet.petName = ?; ', [userId, petName], function (error, result) {
        if (error) {
            return res.status(500).json({ error: error.message });
        }
        if (result.length === 0) {
            return res.status(404).json({ message: "No results found." });
        }
        res.json({
            status: "success",
            petName: petName,
            petAge: result[0].petAge,
            petBirth: result[0].petBirth,
            result: result.map(item => ({
                vectDate: item.vectDate,
                vectName: item.vectName,
                vectProb: item.vectProb
            }))
        })
        if (db.state === 'connected') {
            // 연결 종료
            // db.end();
        }
    })
})

module.exports = router;