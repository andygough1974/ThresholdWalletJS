'use strict';

module.exports =  {
	dbUrl : process.env.DB_URL || 'mongodb://localhost:27017/threshold',
	db_user: process.env.DB_USER || 'threshold',
	db_password: process.env.DB_USER || 'threshold',
	port: process.env.APP_PORT || 3000,
	smart_contract_api : process.env.SMART_API || 'http://162.243.147.4/api',
	is_mock : true,
	mailgun_public_key:'pubkey-acdf07e1a38a1e8e49f88d7346cbe96e',
	mailgun_api_key: 'key-c63a7af8b4e6a1cafcff893cf6a39577',
	mailgun_domain:'sandbox6c5d19143d384fc992ff06d30474ab15.mailgun.org',
	email_support: 'info@generalbitcoin.io',
	twitter_key:'bTr3k2SlzGLfCCPUoPwa9yKJU',
	twitter_secret:'hJwB10216BtMlQdlwLahg3XJm5SzA9RwkKGcMkqNqiypdHz6ub',
	twitter_callback:'http://localhost:3000/auth/twitter/callback',
	twitter_token_secret:'',
	twitter_access_token : '',
	twitter_returnUrl:''
};
