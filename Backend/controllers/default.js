exports.install = function() {
	framework.route('/api/createWallet', mock, { flags : ['get', 'post'], timeout: 100000 });
	framework.route('/api/sendMoney', mock, { flags : ['get', 'post'], timeout: 100000 });
	framework.route('/api/getBalance', mock, { flags : ['get', 'post'], timeout: 100000 });

	//F.route('/', view_index);
    // or
    // F.route('/');
};

function mock() {
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