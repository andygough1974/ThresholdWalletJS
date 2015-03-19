'use strict';

//API Routes

module.exports = function(app) {

    app.route('/api/sendMoney')
        .post(function onRequest(req, res, next) {
            // implementation here
            res.send(200).json({result: true})
        });

    app.route('/api/createWallet')
        .post(function onRequest(req, res, next) {
            // implementation here
            res.send(200).json({result: true})
        });
}


function setupResponseCallback(res) {

    return function(error, returnValue) {
        if (error) {
            console.error(error);
            return res.json(500, error);
        }

        res.status(200).json(returnValue);
    };
}
