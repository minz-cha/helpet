var express = require('express');
var router = express.Router();
var db = require('../../db');
const path = require('path');
const fs = require('fs');
const form = new FormData();

// async function sendMultipart(req) {
//     try {
//         var form = new FormData();
//         form.append('userId', req.body.userId);
//         form.append('petImg', req.file.buffer, {
//             filename: req.file.originalname,
//             contentType: req.file.mimetype,
//         });
//         form.append('petSpecies', req.body.petSpecies);
//         form.append('petName', req.body.petName);
//         form.append('petAge', req.body.petAge);
//         form.append('petBirth', req.body.petBirth);
//         form.append('petGender', req.body.petGender);

// const response = await axios.post('http://localhost:3000/api/pet/register', form, {
//     headers: {
//         'Content-Type': 'multipart/form-data',
//     },
// });
//         res.setHeader('Content-Type', 'multipart/form-data');
//         return response.data;
//     } catch (error) {
//         throw error;
//     }
// }

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
    // db.query('SELECT CONVERT(petImg USING utf8)  from pet where userId = ? and petName = ?', [userId, petName], function (error, result) {
    //         if (error) {
    //             return res.status(500).json({ error: error.message });
    //         }
    //         const petImg = result[0].petImg;
    //     })
}

//이미지 처리 api
exports.Img = (req, res) => {
    const file = req.file;
    // const petName = req.body.petName;
    // const userId = req.body.userId;

    // const category = req.body.category;
    var imgName = file.filename;

    const filePath = path.join(__dirname, '..', 'main', 'uploads', imgName);
    const imageBuffer = fs.readFileSync(filePath);
    let encode = Buffer.from(imageBuffer).toString('base64');

    res.json({
        "result": encode
    })

    // pet등록 - where userId, petName
    // if (category == '1') {
    //     db.query('INSERT INTO pet (petImg) VALUES (?) where petName = ? and userId = ?', [imageBuffer, petName, userId], (error, results) => {
    //         if (error) {
    //             console.error('Error saving image to MySQL:', error);
    //             return res.status(500).send('Internal Server Error');
    //         }
    //     });
    // }

    // if (category == '2') {
    //     db.query('INSERT INTO test (testImg) VALUES (?)', [imageBuffer], (error, results) => {
    //         if (error) {
    //             console.error('Error saving image to MySQL:', error);
    //             return res.status(500).send('Internal Server Error');
    //         }
    //     });
    // }
    // res.sendFile(filePath)
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

        // db.query('SELECT CONVERT(petImg USING utf8)  from pet where userId = ? and petName = ?', [userId, petName], function (error, result) {
        //     if (error) {
        //         return res.status(500).json({ error: error.message });
        //     }
        //     const petImg = result[0].petImg;
        // })

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

    db.query('DELETE FROM pet WHERE petName = ? and userId = ?', [petName, userId], function (error, data) {
        if (error) {
            return res.status(500).json({ error: error.message });
        }
        res.json({
            status: "success",
            userId: userId,
            petName: petName,
        })
        // if (db.state === 'connected') {
        //     // 연결 종료
        //     // db.end();
        // }
    })
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
            status: "success",
            petName: petName,
            petAge: result[0].petAge,
            petBirth: result[0].petBirth,
            result: result.map(item => ({
                vectImg: item.vectImg,
                vectDate: item.vectDate,
                vectName: item.vectName,
                vectProb: item.vectProb
            }))
        })
        // if (db.state === 'connected') {
        //     // 연결 종료
        //     // db.end();
        // }
    })
}

//진단결과 저장
exports.petListSave = (req, res) => {
    const file = req.file;

    var userId = req.body.userId;
    var petName = req.body.petName;
    var vectDate = req.body.vectDate;
    var vectName = req.body.vectName;
    var vectProb = req.body.vectProb;
    var vectContent = req.body.vectContent;

    var imgName = file.filename;
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

        db.query('INSERT INTO diag_pet(diagIdx, petIdx, vectImg, vectDate, vectName, vectProb, vectContent) VALUES (?,?,?,?,?,?,?)', [null, petIdx, vectImg, vectDate, vectName, vectProb, vectContent], function (error, result) {
            if (error) {
                return res.status(500).json({ error: error.message });
            }
            res.json({
                status: "success"
            })
        })
    })
}