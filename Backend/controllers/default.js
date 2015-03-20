exports.install = function() {
	framework.route('/api/createWallet', createWallet, { flags : ['get', 'post'], timeout: 100000 });
	framework.route('/api/sendMoney', sendMoney, { flags : ['get', 'post'], timeout: 100000 });
	framework.route('/api/getBalance', getBalance, { flags : ['get', 'post'], timeout: 100000 });

	//F.route('/', view_index);
    // or
    // F.route('/');
};

function createWallet() {

	// we could call any function from model like this
	//MODEL('Alice')
	// MODEL('Bob')

	var address = '12AVMBVJDuDsijdTmjXqH39MgmrtmHhdre';
	// just a mock
	self.json(
		{
			result : true,
			data : {
				address : address,
				link : 'http://explorer.chain.com/addresses/' + address
			}
		}
	);
}

function sendMoney() {

	// just a mock
	self.json(
		{
			result : true,
			data : {
				address : address,
				link : 'http://explorer.chain.com/addresses/' + address
			}
		}
	);
}

function getBalance() {

	// just a mock
	self.json(
		{
			result : true,
			data : {
				address : address,
				link : 'http://explorer.chain.com/addresses/' + address
			}
		}
	);

}