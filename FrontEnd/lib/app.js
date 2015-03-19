'use strict';

//Application Routes

module.exports = function(app) {

    app.route('/').get(function onRequest(req, res, next) {
        res.render('index.ejs');
    });
}
