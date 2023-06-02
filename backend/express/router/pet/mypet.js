var express = require('express');
var router = express.Router();
var db = require('../../db');
const multer = require("multer");
const path = require('path');
// const axios = require('axios');
const FormData = require('form-data');

// const formidable = require('formidable');
const fs = require('fs');

const storage = multer.diskStorage({
    destination: function (req, file, cb) {
        cb(null, 'uploads/');
    },
    filename: function (req, file, cb) {
        const ext = path.extname(file.originalname);
        const fileName = `${path.basename(
            file.originalname,
            ext
        )}_${Date.now()}${ext}`;
        cb(null, fileName);
    }
});

const limits = { fileSize: 5 * 1024 * 1024 };
const upload = multer({ storage, limits });

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
        // if (db.state === 'connected') {
        //     // 연결 종료
        //     // db.end();
        // }
    })
})

router.post('/upload', upload.single('petImg'), async (req, res) => {
    const file = req.file;
    const fileStream = fs.createReadStream(file.path);

    form.append('userId', req.body.userId);
    form.append('petSpecies', req.body.petSpecies);
    form.append('petName', req.body.petName);
    form.append('petAge', req.body.petAge);
    form.append('petBirth', req.body.petBirth);
    form.append('petGender', req.body.petGender);
    form.append('petImg', fileStream, file.filename);
    res.setHeader('Content-Type', 'multipart/form-data');
    form.pipe(res);

    console.log(form)
})

// router.post('/upload', upload.single('petImg'), async (req, res) => {
//     try {
//         const file = req.file;
//         const formData = new FormData();

//         // 클라이언트에서 받은 이미지 파일을 formData에 추가합니다.
//         formData.append('petImg', file.buffer, {
//             filename: file.originalname,
//             contentType: file.mimetype,
//         });

//         // 기타 필요한 데이터도 formData에 추가합니다.
//         formData.append('userId', req.body.userId);
//         formData.append('petSpecies', req.body.petSpecies);
//         formData.append('petName', req.body.petName);
//         formData.append('petAge', req.body.petAge);
//         formData.append('petBirth', req.body.petBirth);
//         formData.append('petGender', req.body.petGender);

//         // FormData를 multipart/form-data 형식으로 서버에 전송합니다.
//         const response = await axios.post('http://localhost:3000/api/pet/register', formData, {
//             headers: formData.getHeaders(),
//         });

//         // 서버에서 받은 응답을 클라이언트에게 전달합니다.
//         res.json(response.data);
//     } catch (error) {
//         console.error(error);
//         res.status(500).json({ error: 'Internal Server Error' });
//     }
// });

router.post("/register", upload.single('petImg'), (req, res) => {
    const file = req.file;

    var userId = req.body.userId;
    var petImg = file.originalname;
    var petSpecies = req.body.petSpecies;
    var petName = req.body.petName;
    var petAge = req.body.petAge;
    var petBirth = req.body.petBirth;
    var petGender = req.body.petGender;

    console.log(userId); // userId 출력
    console.log(file); // 파일 정보 출력

    db.query('INSERT INTO pet (petIdx, userId, petImg, petSpecies, petName, petAge, petBirth, petGender) VALUES (?,?,?,?,?,?,?,?)', [null, userId, petImg, petSpecies, petName, petAge, petBirth, petGender], function (error, data) {
        if (error) {
            return res.status(500).json({ error: error.message });
        }

        db.query('SELECT CONVERT(petImg USING utf8)  from pet where userId = ? and petName = ?', [userId, petName], function (error, result) {
            if (error) {
                return res.status(500).json({ error: error.message });
            }
            const petImg = result[0].petImg;
        })

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
        // if (db.state === 'connected') {
        //     // 연결 종료
        //     // db.end();
        // }
    })
});

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
router.post("/list-save", upload.single('vectImg'), (req, res) => {
    const file = req.file;

    var userId = req.body.userId;
    var petName = req.body.petName;
    var vectImg = file.originalname;
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