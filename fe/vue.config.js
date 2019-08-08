// vue.config.js
module.exports = {
  devServer: {
    proxy: 'http://localhost:8081/',
  },
  css: {
    loaderOptions: {
      sass: {
        data: `
          @import "@/assets/styleGuide/colors.scss";
        `
      }
    }
  }
};
