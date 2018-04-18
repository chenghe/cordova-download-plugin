// cordova.define("zsmarter-pdfutil.pdfutil", function(require, exports, module) {
var exec = require('cordova/exec');
exports.download = function( success, error,params) {
    // , ,Android de plugin class ,action,params
    exec(success, error, "Plugin_ZS_PdfUtil", "Download", [params]);
};

exports.openPdf = function( success, error,params) {
    // , ,Android de plugin class ,action,params
    exec(success, error, "Plugin_ZS_PdfUtil", "OpenPdf", [params]);
};
// });
