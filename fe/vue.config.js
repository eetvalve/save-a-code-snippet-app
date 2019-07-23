// vue.config.js
module.exports = {
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
