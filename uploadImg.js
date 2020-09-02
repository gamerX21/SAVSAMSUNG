/*
Author: Houssem  Kraoua
*/
var crypto = require('crypto');
var uuid = require('uuid');
var express = require('express');
var mysql = require('mysql');
var bodyParser = require('body-parser');
var storage, path;
var multer = require('multer')
var path = require('path');
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

var fs = require('fs');

storage = multer.diskStorage({
    destination: './uploads/',
    filename: function (req, file, cb) {
        return crypto.pseudoRandomBytes(16, function (err, raw) {
            if (err) {
                return cb(err);
            }
            return cb(null, "" + (raw.toString('hex')) + (path.extname(file.originalname)));
        });
    }
});



app.get('/uploads/:upload', function (req, res) {
    file = req.params.upload;
    console.log(req.params.upload);
    var img = fs.readFileSync(__dirname + "/uploads/" + file);
    res.writeHead(200, { 'Content-Type': 'image/png' });
    res.end(img, 'binary');

});
app.get('/uploadsForJPG/:upload', function (req, res) {
    file = req.params.upload;
    console.log(req.params.upload);
    var img = fs.readFileSync(__dirname + "/uploads/" + file);
    res.writeHead(200, { 'Content-Type': 'image/jpg' });
    res.end(img, 'binary');

});
app.listen(3000, () => {
    console.log('Restful Running on port 3000');
})