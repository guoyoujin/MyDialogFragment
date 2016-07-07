MDDialog
Android Arsenal

English
前言

MDDialog是一款Material Designed风格的Dialog，可以灵活定制其内容以及显示方式，如：可以添加中间的ContentView，可以对ContentView自由的添加代码操作，如添加点击事件等等；对于多个选项风格的dialog，提供了直接设置多个选项的功能，并提供了精细的UI按下效果、点击回调函数等等。

介绍

这款Material Designed风格的Dialog的设计灵感来自于MD设计理念，你可以通过使用和AlertDialog相似的代码来构建MDDialog。

MDDialog具有多种有趣的属性：
1. 可以设置显示/隐藏title、显示/隐藏 确定/取消按钮（或者同时隐藏两个button，具体UI效果可见微信的选择对话框）；
2. 可以向MDDialog添加一个自定义的View，同时可以在构建MDDialog时，使用setContentViewOperator(...)函数，添加操作自定义view的代码；
3. 可以给MDDialog设置String[] messages，构建MDDialog的builder中提供了响应点击每一个String的回调函数，即，通过setOnItemClickListener(...)设置点击每一条目的回调；
4. 可以自由定制MDialog的四个角的半径大小；
5. MDDialog自动为每个message提供了按下效果，且按下效果会随着此item是否具有圆角而改变；
6. 可以通过两种方式设置MDDialog的宽度，使用宽度占整个屏幕宽度的比值，或使用是精确的尺寸；

Author:Carbs.Wang
Email:yeah0126#yeah.net

截图

you can check the MDDialog_setView.png 
添加message MDDialog_setMessages
使用

首先添加依赖

  dependencies {
    compile 'cn.carbs.android:MDDialog:1.0.0'
  }
在java代码中构造MDDialog

可以构建两种不同模式的MDDialog，这两种模式不能够同时使用
  1.为MDDialog设置自定义View:
  new MDDialog.Builder(ActivityMain.this)
//              .setContentView(customizedView)
                .setContentView(R.layout.content_dialog)
                .setContentViewOperator(new MDDialog.ContentViewOperator() {
                  @Override
                  public void operate(View contentView) {//这里的contentView就是上面代码中传入的自定义的View或者layout资源inflate出来的view
                    EditText et = (EditText) contentView.findViewById(R.id.edit0);
                    et.setHint("hint set in operator");
                  }
                })
                .setTitle("添加")
                .setNegativeButton(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                  }
                })
                .setPositiveButton(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                  }
                })
                .setPositiveButtonMultiListener(new MDDialog.OnMultiClickListener() {
                  @Override
                    public void onClick(View clickedView, View contentView) {
                    //这里的contentView就是上面代码中传入的自定义的View或者layout资源inflate出来的view，目的是方便在确定/取消按键中对contentView进行操作，如获取数据等。
                      EditText et = (EditText) contentView.findViewById(R.id.edit0);
                      Toast.makeText(getApplicationContext(), "edittext 0 : " + et.getText(), Toast.LENGTH_SHORT).show();
                    }
                  })
                .setNegativeButtonMultiListener(new MDDialog.OnMultiClickListener() {
                  @Override
                  public void onClick(View clickedView, View contentView) {
                    EditText et = (EditText) contentView.findViewById(R.id.edit1);
                    Toast.makeText(getApplicationContext(), "edittext 1 : " + et.getText(), Toast.LENGTH_SHORT).show();
                  }
                })
                .setWidthMaxDp(600)
//              .setShowTitle(false)//default is true
//              .setShowButtons(true)//default is true
                .create()
              .show();

    2.设置 String[] messages，每一个String都将对应一个item，具体可以看效果图

    final String[] messages = new String[]{"两个黄鹂鸣翠柳，一行白鹭上青天。",
                "窗含西岭千秋雪，门泊东吴万里船。",
                "君不见，黄河之水天上来，奔流到海不复回；君不见，高堂明镜悲白发，朝如青丝暮如雪。"};
    new MDDialog.Builder(ActivityMain.this)
                        .setMessages(messages)
                        .setTitle("一首古诗")
                        .setNegativeButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        })
                        .setPositiveButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "positive", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnItemClickListener(new MDDialog.OnItemClickListener() {
                            @Override
                            public void onItemClicked(int index) {
                                Toast.makeText(getApplicationContext(), messages[index], Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setWidthMaxDp(600)
                        .setShowTitle(false)
                        .setShowButtons(true)
                        .create()
                      .show();

English
Abstract

a material designed style dialog, can add operation of content view, set messages, respond to onclick of messages items and so on

Introduction

the MDDialog inspired by the Material Design Pattern. You can get a MD style dialog quickly by using MDDialog Builder. this MDDialog has many interesting configurations such as :

you can hide or show title or positive/negative button(or both)
you can add a customized view into the MDDialog, and can add code to control the customized view in Builder' fuction setContentViewOperator(...)
you can set an String[] messages into MDDialog, and each item of messages will be one view in MDDialog, and can set setOnItemClickListener(...) for each item of messages.
you can customize the corner's radius of MDDialog.
MDDialog has the pressed effect of each messages item, and the shape of pressed effect is the same as item's shape
two ways to set the MDDialog width, the ratio of screen width, or the exact dimension

Author:Carbs.Wang 
Email:yeah0126#yeah.net
Screenshot

you can check the MDDialog_setView.png 
SegmentControlView MDDialog_setMessages
Example

first add dependences

  dependencies {
    compile 'cn.carbs.android:MDDialog:1.0.0'
  }
how to build the MDDialog in java code

two mode to create MDDialog
  1.set customize view :
  new MDDialog.Builder(ActivityMain.this)
//              .setContentView(customizedView)
                .setContentView(R.layout.content_dialog)
                .setContentViewOperator(new MDDialog.ContentViewOperator() {
                  @Override
                  public void operate(View contentView) {
                    EditText et = (EditText) contentView.findViewById(R.id.edit0);
                    et.setHint("hint set in operator");
                  }
                })
                .setTitle("添加")
                .setNegativeButton(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                  }
                })
                .setPositiveButton(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                  }
                })
                .setPositiveButtonMultiListener(new MDDialog.OnMultiClickListener() {
                  @Override
                    public void onClick(View clickedView, View contentView) {
                      EditText et = (EditText) contentView.findViewById(R.id.edit0);
                      Toast.makeText(getApplicationContext(), "edittext 0 : " + et.getText(), Toast.LENGTH_SHORT).show();
                    }
                  })
                .setNegativeButtonMultiListener(new MDDialog.OnMultiClickListener() {
                  @Override
                  public void onClick(View clickedView, View contentView) {
                    EditText et = (EditText) contentView.findViewById(R.id.edit1);
                    Toast.makeText(getApplicationContext(), "edittext 1 : " + et.getText(), Toast.LENGTH_SHORT).show();
                  }
                })
                .setWidthMaxDp(600)
//              .setShowTitle(false)//default is true
//              .setShowButtons(true)//default is true
                .create()
              .show();

    2.set String[] messages

    final String[] messages = new String[]{"两个黄鹂鸣翠柳，一行白鹭上青天。",
                "窗含西岭千秋雪，门泊东吴万里船。",
                "君不见，黄河之水天上来，奔流到海不复回；君不见，高堂明镜悲白发，朝如青丝暮如雪。"};
    new MDDialog.Builder(ActivityMain.this)
                        .setMessages(messages)
                        .setTitle("一首古诗")
                        .setNegativeButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        })
                        .setPositiveButton(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "positive", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnItemClickListener(new MDDialog.OnItemClickListener() {
                            @Override
                            public void onItemClicked(int index) {
                                Toast.makeText(getApplicationContext(), messages[index], Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setWidthMaxDp(600)
                        .setShowTitle(false)
                        .setShowButtons(true)
                        .create()
                      .show();

License

Copyright 2016 Carbs.Wang (MDDialog)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
