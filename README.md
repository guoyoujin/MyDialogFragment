# MyDialogFragment

#这个项目来源
原作者是：Carbs0126 地址：https://github.com/Carbs0126/MDDialog<br>
感谢 Carbs0126 他的github地址：https://github.com/Carbs0126]

## 前言
`MyDialogFragment`是一款`Material Designed`风格的`Dialog`，可以灵活定制其内容以及显示方式，如：可以添加中间的`ContentView`，可以对`ContentView`自由的添加代码操作，如添加点击事件等等；对于多个选项风格的dialog，提供了直接设置多个选项的功能，并提供了精细的UI按下效果、点击回调函数等等。

## 介绍
这款`Material Designed`风格的`Dialog`的设计灵感来自于MD设计理念，你可以通过使用和`AlertDialog`相似的代码来构建`MyDialogFragment`。

`MyDialogFragment`具有多种有趣的属性：<br>
1. 可以设置显示/隐藏title、显示/隐藏 确定/取消按钮（或者同时隐藏两个button，具体UI效果可见微信的选择对话框）；<br>
2. 可以向`MyDialogFragment`添加一个自定义的`View`，同时可以在构建`MyDialogFragment`时，使用`setContentViewOperator(...)`函数，添加操作自定义view的代码；<br>
3. 可以给`MyDialogFragment`设置`String[] messages`，构建`MyDialogFragment`的`builder`中提供了响应点击每一个`String`的回调函数，即，通过`setOnItemClickListener(...)`设置点击每一条目的回调；<br>
4. 可以自由定制`MyDialogFragment`的四个角的半径大小；<br>
5. `MyDialogFragment`自动为每个`message`提供了按下效果，且按下效果会随着此`item`是否具有圆角而改变；<br>
6. 可以通过两种方式设置`MyDialogFragment`的宽度，使用宽度占整个屏幕宽度的比值，或使用是精确的尺寸；<br>
<br>
  Author:Carbs.Wang<br>
  Email:yeah0126#yeah.net<br>

## Screenshot
<center>
![you can check the 1467886864394.gif](https://github.com/guoyoujin/MyDialogFragment/blob/master/art/1467886864394.gif)
</center><br>
## 使用
 首先添加依赖
```
  dependencies {
    compile 'com.trycatch.android:mydialogfragment:1.0.0'
  }
```
 在`java`代码中构造`MyDialogFragment`
```
可以构建两种不同模式的MyDialogFragment，这两种模式不能够同时使用
  1.为MyDialogFragment设置自定义View:
  new BaseDialogFragment.Builder(MainActivity.this)
                            .setContentView(R.layout.content_dialog)
                            .setContentViewOperator(new BaseDialogFragment.ContentViewOperator() {
                                @Override
                                public void operate(View contentView) {
                                    EditText et = (EditText)contentView.findViewById(R.id.edit0);
                                    et.setHint("hint set in operator");
                                }
                            })
    //                      .setMessages(messages)
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
                            .setPositiveButtonMultiListener(new BaseDialogFragment.OnMultiClickListener() {

                                @Override
                                public void onClick(View clickedView, View contentView) {
                                    EditText et = (EditText)contentView.findViewById(R.id.edit0);
                                    Toast.makeText(getApplicationContext(), "edittext 0 : " + et.getText(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButtonMultiListener(new BaseDialogFragment.OnMultiClickListener() {

                                @Override
                                public void onClick(View clickedView, View contentView) {
                                    EditText et = (EditText)contentView.findViewById(R.id.edit1);
                                    Toast.makeText(getApplicationContext(), "edittext 1 : " + et.getText(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setOnItemClickListener(new BaseDialogFragment.OnItemClickListener() {
                                @Override
                                public void onItemClicked(int index) {
                                    if (index == 0) {
                                        Toast.makeText(getApplicationContext(), "index 0", Toast.LENGTH_SHORT).show();
                                    } else if (index == 1) {
                                        Toast.makeText(getApplicationContext(), "index 1", Toast.LENGTH_SHORT).show();
                                    } else if (index ==2 ){
                                        Toast.makeText(getApplicationContext(), "index 2", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setWidthMaxDp(600)
                            .setShowButtons(true)
                            .create()
                            .show(MainActivity.this.getSupportFragmentManager(),"Base");

    2.设置 String[] messages，每一个String都将对应一个item，具体可以看效果图

    final String[] messages = new String[]{"两个黄鹂鸣翠柳，一行白鹭上青天。",
                    "窗含西岭千秋雪，门泊东吴万里船。",
                    "君不见，黄河之水天上来，奔流到海不复回；君不见，高堂明镜悲白发，朝如青丝暮如雪。"};
        final String MESSAGE_TAG = "BaseFragmentMessage";
                button4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new BaseDialogFragment.Builder(MainActivity.this)
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
                            .setOnItemClickListener(new BaseDialogFragment.OnItemClickListener() {
                                @Override
                                public void onItemClicked(int index) {
                                    Toast.makeText(getApplicationContext(), messages[index], Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setWidthMaxDp(600)
                            .setShowButtons(true)
                            .create()
                            .show(MainActivity.this.getSupportFragmentManager(),MESSAGE_TAG);
                    }
                });

```


#English

The project comes from
Carbs0126 (https://github.com/Carbs0126/MDDialog]
Think you Carbs0126 (https://github.com/Carbs0126]

## Abstract
a material designed style dialog, can add operation of content view, set messages, respond to onclick of messages items and so on

## Introduction
the `MyDialogFragment` inspired by the `Material Design` Pattern. You can get a MD style dialog quickly by using `MyDialogFragment Builder`.
this `MyDialogFragment` has many interesting configurations such as :
  1. you can hide or show title or positive/negative button(or both)
  2. you can add a `customized view` into the `MyDialogFragment`, and can add code to control the customized view in Builder' fuction `setContentViewOperator(...)`
  3. you can set an `String[] messages` into `MyDialogFragment`, and each item of messages will be one view in `MyDialogFragment`, and can set `setOnItemClickListener(...)` for each item of messages.
  4. you can customize the corner's radius of `MyDialogFragment`.
  5. `MyDialogFragment` has the pressed effect of each messages item, and the shape of pressed effect is the same as item's shape
  6. two ways to set the `MyDialogFragment` width, the ratio of screen width, or the exact dimension<br>
  <br>
  Author:guoyoujin
  <br>
  Email:guoyoujin123@gmail.com

## Screenshot
<center>
![you can check the 1467886864394.gif](https://github.com/guoyoujin/MyDialogFragment/blob/master/art/1467886864394.gif)
</center><br>

## Example
 first add dependences
```
  dependencies {
    compile 'com.trycatch.android:mydialogfragment:1.0.0'
  }
```
 how to build the `MyDialogFragment` in java code
```
two mode to create MyDialogFragment
  1.set customize view :
  new BaseDialogFragment.Builder(MainActivity.this)
                            .setContentView(R.layout.content_dialog)
                            .setContentViewOperator(new BaseDialogFragment.ContentViewOperator() {
                                @Override
                                public void operate(View contentView) {
                                    EditText et = (EditText)contentView.findViewById(R.id.edit0);
                                    et.setHint("hint set in operator");
                                }
                            })
    //                      .setMessages(messages)
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
                            .setPositiveButtonMultiListener(new BaseDialogFragment.OnMultiClickListener() {

                                @Override
                                public void onClick(View clickedView, View contentView) {
                                    EditText et = (EditText)contentView.findViewById(R.id.edit0);
                                    Toast.makeText(getApplicationContext(), "edittext 0 : " + et.getText(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButtonMultiListener(new BaseDialogFragment.OnMultiClickListener() {

                                @Override
                                public void onClick(View clickedView, View contentView) {
                                    EditText et = (EditText)contentView.findViewById(R.id.edit1);
                                    Toast.makeText(getApplicationContext(), "edittext 1 : " + et.getText(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setOnItemClickListener(new BaseDialogFragment.OnItemClickListener() {
                                @Override
                                public void onItemClicked(int index) {
                                    if (index == 0) {
                                        Toast.makeText(getApplicationContext(), "index 0", Toast.LENGTH_SHORT).show();
                                    } else if (index == 1) {
                                        Toast.makeText(getApplicationContext(), "index 1", Toast.LENGTH_SHORT).show();
                                    } else if (index ==2 ){
                                        Toast.makeText(getApplicationContext(), "index 2", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setWidthMaxDp(600)
                            .setShowButtons(true)
                            .create()
                            .show(MainActivity.this.getSupportFragmentManager(),"Base");

    2.set String[] messages

    final String[] messages = new String[]{"两个黄鹂鸣翠柳，一行白鹭上青天。",
                    "窗含西岭千秋雪，门泊东吴万里船。",
                    "君不见，黄河之水天上来，奔流到海不复回；君不见，高堂明镜悲白发，朝如青丝暮如雪。"};
        final String MESSAGE_TAG = "BaseFragmentMessage";
                button4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new BaseDialogFragment.Builder(MainActivity.this)
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
                            .setOnItemClickListener(new BaseDialogFragment.OnItemClickListener() {
                                @Override
                                public void onItemClicked(int index) {
                                    Toast.makeText(getApplicationContext(), messages[index], Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setWidthMaxDp(600)
                            .setShowButtons(true)
                            .create()
                            .show(MainActivity.this.getSupportFragmentManager(),MESSAGE_TAG);
                    }
                });

```

## License

    Copyright 2016 guoyoujin (MyDialogFragment)
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.