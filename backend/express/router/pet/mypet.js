var express = require('express');
var router = express.Router();
var db = require('../../db');
const path = require('path');
const fs = require('fs');
const form = new FormData();

// 반려동물 홈화면
exports.petMain = (req, res) => {
    var userId = req.body.userId;

    db.query('SELECT userId, CONVERT(petImg USING utf8) as petImg, petSpecies, petName, petAge, petBirth, petGender FROM pet WHERE userId = ?', [userId], function (error, result) {
        if (error) {
            return res.status(500).json({ error: error.message });
        }
        res.json({
            status: "success",
            result: result
        })
        // if (db.state === 'connected') {
        //     // 연결 종료
        //     // db.end();
        // }
    })
}

// 반려동물 등록
exports.petRegister = (req, res) => {
    const file = req.file;

    var userId = req.body.userId;
    var petSpecies = req.body.petSpecies;
    var petName = req.body.petName;
    var petAge = req.body.petAge;
    var petBirth = req.body.petBirth;
    var petGender = req.body.petGender;

    var imgName = file.filename;
    const filePath = path.join(__dirname, '..', 'main', 'uploads', imgName);
    const imageBuffer = fs.readFileSync(filePath);
    let encode = Buffer.from(imageBuffer).toString('base64');

    console.log(userId); // userId 출력
    console.log(file); // 파일 정보 출력

    db.query('INSERT INTO pet (petIdx, userId, petImg, petSpecies, petName, petAge, petBirth, petGender) VALUES (?,?,?,?,?,?,?,?)', [null, userId, encode, petSpecies, petName, petAge, petBirth, petGender], function (error, data) {
        if (error) {
            return res.status(500).json({ error: error.message });
        }

        res.json({
            status: "success"
        })
        // if (db.state === 'connected') {
        //     // 연결 종료
        //     // db.end();
        // }
    })
}


// 반려동물 삭제
exports.petDelete = (req, res) => {
    var userId = req.body.userId;
    var petName = req.body.petName;

    db.query('SELECT petIdx FROM pet WHERE petName = ? and userId = ?', [petName, userId], function (error, petData) {
        if (error) {
            return res.status(500).json({ error: error.message });
        }

        if (petData.length > 0) {
            const petIdx = petData[0].petIdx;
            db.query('DELETE FROM diag_pet WHERE petIdx = ?', [petIdx], function (error, data) {
                if (error) {
                    return res.status(500).json({ error: error.message });
                }

                db.query('DELETE FROM pet WHERE petName = ? and userId = ?', [petName, userId], function (error, data) {
                    if (error) {
                        return res.status(500).json({ error: error.message });
                    }

                    res.json({
                        status: "success",
                    });
                });
            });
        } else {
            db.query('DELETE FROM pet WHERE petName = ? and userId = ?', [petName, userId], function (error, data) {
                if (error) {
                    return res.status(500).json({ error: error.message });
                }
                res.json({
                    status: "success",
                });
            });
        }
    });
}
//진단결과 리스트 조회 (진단리스트 홈화면)
exports.petList = (req, res) => {
    var userId = req.body.userId;
    var petName = req.body.petName;

    db.query('select pet.petName, pet.petAge, pet.petBirth,  CONVERT(diag_pet.vectImg USING utf8) as vectImg,  diag_pet.vectDate, diag_pet.vectName, diag_pet.vectProb from pet join diag_pet on pet.petIdx = diag_pet.petIdx where pet.userId = ? and pet.petName = ?; ', [userId, petName], function (error, result) {
        if (error) {
            return res.status(500).json({ error: error.message });
        }
        if (result.length === 0) {
            return res.status(404).json({ message: "No results found." });
        }
        res.json({
            petAge: result[0].petAge,
            petBirth: result[0].petBirth,
            petName: result[0].petName,
            result: result.map(item => ({
                vectImg: item.vectImg,
                vectDate: item.vectDate,
                vectName: item.vectName.split(","), 
                vectProb: item.vectProb
            })),
            status: "success"
        });
        // if (db.state === 'connected') {
        //     // 연결 종료
        //     // db.end();
        // }
    })
}//진단결과 저장
exports.petListSave = (req, res) => {
    const file = req.file;

    var userId = req.body.userId;
    console.log("userId", userId)
    var petName = req.body.petName;
    console.log("petName", petName)
    var vectDate = req.body.vectDate;
    console.log("vectDate", vectDate)
    var vectName = req.body.vectName;
    console.log("vectName", vectName)
    var vectProb = req.body.vectProb;
    console.log("vectProb", vectProb)
    var vectContent = req.body.vectContent;
    console.log("vectContent", vectContent)

    var imgName = file.filename;
    console.log(imgName)
    const filePath = path.join(__dirname, '..', 'main', 'uploads', imgName);
    const imageBuffer = fs.readFileSync(filePath);
    let vectImg = Buffer.from(imageBuffer).toString('base64');

    db.query('select petIdx from pet where userId = ? and petName = ?', [userId, petName], function (error, result) {
        if (error) {
            return res.status(500).json({ error: error.message });
        }
        if (result.length === 0) {
            return res.status(404).json({ message: "No results found." });
        }
        const petIdx = result[0].petIdx;

        console.log("petIdx", petIdx)

        db.query('INSERT INTO diag_pet(diagIdx, petIdx, vectImg, vectDate, vectName, vectProb, vectContent) VALUES (?,?,?,?,?,?,?)', [null, petIdx, vectImg, vectDate, JSON.stringify(vectName), vectProb, vectContent], function (error, result) {
            if (error) {
                console.log("error", error.message)
                return res.status(500).json({ error: error.message });
            }
            res.json({
                status: "success"
            })
        })
    })
}
