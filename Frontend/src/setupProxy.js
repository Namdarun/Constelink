const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  // app.use(
  //   ["/files/saveimg"],
  //   createProxyMiddleware({
  //     target: 'http://j8a206.p.ssafy.io:8996',
  //     changeOrigin: true,
  //   })
  // )
  
  // app.use(
  //   ["/auth", "/member", "/donations"],
  //   createProxyMiddleware({
  //     target: 'http://j8a206.p.ssafy.io/',
  //     changeOrigin: true,
  //   })
  // )

  app.use(
    ["/notices" ,"/files/saveimg", "/beneficiary",
    "/fundraising", "/member", 
     ],
    createProxyMiddleware({
      target: "http://j8a206.p.ssafy.io",
      changeOrigin: true,
    })
  )
}
