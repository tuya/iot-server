const path = require('path');
const proxy = require('express-http-proxy');
const app = require('express')();
const serveStatic = require('serve-static');
const open = require('open');

const port = '8888';

app.use(
  '/api',
  proxy('http://localhost:8080', {
    proxyReqPathResolver(req) {
      const { originalUrl } = req;
      // 后端服务的路径无/api，需在前端剔除此部分
      // return originalUrl.replace('/api', '');

      // docker 请求不转译
      return originalUrl;
    },
  }),
);

app.use(serveStatic(path.join(__dirname, '../dist')));
app.use(serveStatic(path.join(__dirname, '../dist/main-app')));

app.get('*', (req, res) => {
  res.sendFile(path.resolve(__dirname, '../dist/main-app/index.html'));
});

app.listen(port, () => {
  console.log(`Server listening at http://127.0.0.1:${port}`);
  if (process.env.debug) {
    open(`http://127.0.0.1:${port}`);
  }
});
