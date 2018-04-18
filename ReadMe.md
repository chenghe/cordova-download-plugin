# zsmarter-pdf

@(插件简介)[何成斌|QQ:472966681]



**zsmarter-pdfutil**
适用于下载并打开pdf,doc等文件

-------------------

[TOC]

##添加方法

> 通过   cordova plugin add zsmarter-pdfutil  添加插件

##使用方法
###下载文件（目前下载完成后自动打开）

 >**参数介绍：**
 >@downloadUrl 下载URL
 >@ downloadPath 下载文件目标目录

#### 调用示例
    
    zsmarter.pdfutil.download(
       function (res) {
           alert(JSON.stringify(res));
           },
           function (err) {
           alert(JSON.stringify(err));
          },                                  		 {downloadUrl:'https://github.com/barteksc/AndroidPdfViewer/archive/master.zip',
      downloadPath:'aaa/'
      });

 > 返回参数
 >
 > ```
 > res 下载完成后的文件绝对路径(String格式返回)
 > ```

###打开本地PDF等文件(openPdf)

#### 参数
 >**参数介绍：**
 >@pdfPath 需要打开文件的相对路径

#### 调用示例
          zsmarter.pdfutil.openPdf(
        	{
            pdfPath:'aaa/aaa.pdf'
            }
            );



-------------------
> **注意：**       
> 1.添加插件前需要在:project/platforms/android下的build.gradle中的dependencies标签中的最下面添加compile 'com.android.support:appcompat-v7:25.0.1' ；compile ’com.android.support:support-v4:24.1.1+‘，
>
> ```
> compile 'com.squareup.okhttp3:okhttp:3.6.0'
> ```
>
> 如果已经添加其他兼容性包，可以忽略此信息。     
>
> 



