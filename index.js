/*
Author: Houssem  Kraoua
*/
var crypto = require('crypto');
var uuid = require('uuid');
var express = require('express');
var mysql = require('mysql');
var bodyParser = require('body-parser');
//Connect to MySQL
var con = mysql.createConnection({
    host: '127.0.0.1',
    port: '3306',
    user: 'root',
    password: '',
    database: 'savsamsung'
});
con.connect((err) => {
    if (!err)
        console.log('Connection Established Successfully');
    else
        console.log('Connection Failed!' + JSON.stringify(err, undefined, 2));
});
var app = express();
app.use(bodyParser.json()); // Accept JSON params
app.use(bodyParser.urlencoded({ extended: true })); //Accept UrlEncoded params

//PASSWORD CRYPT
var genRandomString = function (length) {
    return crypto.randomBytes(Math.ceil(length / 2))
        .toString('hex') //Convert to hexa format
        .slice(0, length);

};
var sha512 = function (password, salt) {
    var hash = crypto.createHmac('sha512', salt); //Use SHA512
    hash.update(password);
    var value = hash.digest('hex');
    return {
        salt: salt,
        passwordHash: value
    };

};
function saltHashPassword(userPassword) {
    var salt = genRandomString(16); //Gen Random string with 16 charachters
    var passwordData = sha512(userPassword, salt);
    return passwordData;

}
function checkHashPassword(userPassword, salt) {
    var passwordData = sha512(userPassword, salt);
    return passwordData;

}
//register User
app.post('/registerUser/', (req, res, next) => {
    var post_data = req.body;
    var uid = uuid.v4();
    var plaint_password = post_data.password;  //Get password from post params
    var hash_data = saltHashPassword(plaint_password);
    var password = hash_data.passwordHash;  //Get Hash value
    var salt = hash_data.salt; //Get salt
    var fullname = post_data.fullname;
    var email = post_data.email;
    var adresse = post_data.adresse;
    var tel = post_data.tel;
    con.query('SELECT * FROM user where email=?', [email], function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length)
            res.json('User already exists');
        else {
            con.query("INSERT INTO `user`(`unique_id`,`fullname`, `email`, `password`, `salt`,`adresse`,`tel`,`created_at`, `updated_at`) VALUES (?,?,?,?,?,?,?,NOW(),NOW())",
                [uid, fullname, email, password, salt, adresse, tel], function (err, result, fields) {
                    if (err) throw err;

                    res.json('votre compte a été crée avec succés veuillez connectez !');

                })
        }
    });

})
//Login USER CHECK:BEGIN
app.post('/loginUser/', (req, res, next) => {
    var post_data = req.body;
    var user_password = post_data.pwd;
    var email = post_data.email;
    con.query('SELECT * FROM user where email=?', [email], function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length) {
            var salt = result[0].salt;
            var encrypted_password = result[0].password;
            var hashed_password = checkHashPassword(user_password, salt).passwordHash;
            if (encrypted_password == hashed_password) {
                res.end(JSON.stringify(result[0]));
            }
            else {
                res.end(JSON.stringify("mot de passe incorrect vérifier vos informations"));
            }
        }
    });
})
//Login USER CHECK:END
//GET USER BY EMAIL:BEGIN
app.get('/getUserByEmail/:email', (req, res, next) => {
    con.query('SELECT id,fullname,email,adresse,tel FROM user where email=?', [req.params.email], function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length) {
            res.end(JSON.stringify(result[0]));
        }
        else {
            res.end(JSON.stringify("user not found"));
        }

    });


})
//GET USER BY EMAIL:END
//GET ALL REPAIRD BY USER ID:BEGIN
app.get('/getAllRepairDbyUser/:id', (req, res, next) => {
    con.query('SELECT * FROM repairdemand where user_id=?', [req.params.id], function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length) {
            res.end(JSON.stringify(result));
        }
        else {
            res.end(JSON.stringify("nothing was found  for repairD for user"));
        }

    });


})
//GET ALL REPAIRD BY USER ID:END
//GET ALL HELPS:BEGIN
app.get('/GetAllHelpModels/', (req, res, next) => {
    con.query('SELECT * FROM helps', function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length) {
            res.end(JSON.stringify(result));
        }
        else {
            res.end(JSON.stringify("nothing was found  for help"));
        }

    });


})
//GET ALL HELPS:END
//GET ALL Articles:BEGIN
app.get('/GetAllArticles/', (req, res, next) => {
    con.query('SELECT * FROM articles', function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length) {
            res.end(JSON.stringify(result));
        }
        else {
            res.end(JSON.stringify("nothing was found  for article"));
        }
    });
})
//GET ALL HELPS:END
//GET ALL Services:BEGIN
app.get('/GetAllServices/', (req, res, next) => {
    con.query('SELECT * FROM services', function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length) {
            res.end(JSON.stringify(result));
        }
        else {
            res.end(JSON.stringify("nothing was found  for services"));
        }
    });
})
//GET ALL Services:END
//GET ARTICLE BY ID:BEGIN
app.get('/getArticleById/:id', (req, res, next) => {
    con.query('SELECT * FROM articles where id=?', [req.params.id], function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length) {
            res.end(JSON.stringify(result[0]));
        }
        else {
            res.end(JSON.stringify("error"));
        }
    });
})
//GET ARTICLE BY ID:END
//GET ARTICLE BY Service type:BEGIN
app.get('/GetAllArticlesByServiceType/:type', (req, res, next) => {
    con.query('SELECT * FROM articles where type=?', [req.params.type], function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length) {
            res.end(JSON.stringify(result));
        }
        else {
            res.end(JSON.stringify("error when getting articles"));
        }
    });
})
//GET ARTICLE BY service type:END
//GET ALL Produits BY Type:BEGIN
app.get('/GetAllProduitsByType/:type', (req, res, next) => {
    con.query('SELECT * FROM produits where type=?', [req.params.type], function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length) {
            res.end(JSON.stringify(result));
        }
        else {
            res.end(JSON.stringify("error when getting produits by type"));
        }
    });
})
//GET Produit BY ID:BEGIN
app.get('/getProduitById/:id', (req, res, next) => {
    con.query('SELECT * FROM produits where id=?', [req.params.id], function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length) {
            res.end(JSON.stringify(result[0]));
        }
        else {
            res.end(JSON.stringify("error when getting produits by type"));
        }

    });


})
//GET ALL Produit BY ID:END
//GET ALL Astuces BY Article ID:BEGIN
app.get('/GetAllAstuces/:articleId', (req, res, next) => {
    con.query('SELECT * FROM astuces where article_id=?', [req.params.articleId], function (err, result, fields) {
        con.on('error', function (err) {
            console.log('[MYSQL ERROR]', err);
        });
        if (result && result.length) {
            res.end(JSON.stringify(result));
        }
        else {
            res.end(JSON.stringify("error when getting astuces by article id"));
        }

    });


})
//GET ALL Astuces BY Article ID:END
//ADD NEW REPAIR DEMAND:BEGIN
app.post('/AddNewRepairDemand/:id', (req, res, next) => {
    var post_data = req.body;  //Get POST params
    var userId = req.params.id;
    var type = post_data.type;
    var ville = post_data.ville;
    var adresse = post_data.adresse;
    var generated_num = post_data.generated_num;
    var phone = post_data.phone;
    var status = post_data.status;
    con.query("INSERT INTO `repairDemand`(`user_id`,`type`,`phone`, `ville`, `adresse`, `generated_num`,`status`,`dataA`) VALUES (?,?,?,?,?,?,?,NOW())",
        [userId, type, phone, ville, adresse, generated_num, status], function (err, result, fields) {
            if (err) throw err;
            res.json('demande de réparation ajoutée avec succés !');

        })

})
//ADD NEW REPAIR DEMAND:END
//ADD NEW CMD DEMAND:BEGIN
app.post('/addNewCommand/:id', (req, res, next) => {
    var post_data = req.body;  //Get POST params
    var userId = req.params.id;
    var produitId = post_data.produitId;
    var ref = post_data.ref;
    var quan = post_data.quan;
    con.query("INSERT INTO `commandes`(`ref`, `user_id`, `produit_id`, `quantité`, `dataA`) VALUES (?,?,?,?,NOW())",
        [ref, userId, produitId, quan], function (err, result, fields) {
            if (err) throw err;
            res.json('commande ajoutée avec succés !');

        })

})
//ADD NEW CMD DEMAND:END
//Start Server
app.listen(1337, () => {
    console.log('Restful Running on port 1337');
})