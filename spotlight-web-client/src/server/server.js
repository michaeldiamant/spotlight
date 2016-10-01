var path = require('path');
var Express = require('express');

var app = Express();

app.use('/styles', Express.static(path.resolve(__dirname, '../client/styles')));
app.use(Express.static(path.resolve(__dirname, '../../dist')));

app.get('/', (req, res) => {
  res.sendFile(path.resolve(__dirname, '../client/index.html'));
});

var server = app.listen(process.env.PORT || 3000, () => {
  console.log('Server is listening on port = %s', server.address().port);
});
