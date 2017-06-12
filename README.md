# NotePad

关于NotePad功能介绍以及详细代码说明

## 实现功能：

**新增便签**

**粘贴便签**

**时间戳**

**模糊查询标题**

## 一、功能实现


### 1、 新增便签

![](https://github.com/vivianxww/images/blob/master/XZ1.jpg)

![](https://github.com/vivianxww/images/blob/master/XZ2.jpg)

![](https://github.com/vivianxww/images/blob/master/XZ3.jpg)

![](https://github.com/vivianxww/images/blob/master/XZ4.jpg)

![](https://github.com/vivianxww/images/blob/master/XZ5CX1.jpg)

### 2、 粘贴便签

![](https://github.com/vivianxww/images/blob/master/NT1.jpg)

![](https://github.com/vivianxww/images/blob/master/NT2.jpg)

![](https://github.com/vivianxww/images/blob/master/NT3.jpg)

![](https://github.com/vivianxww/images/blob/master/NT4.jpg)

![](https://github.com/vivianxww/images/blob/master/NT5.jpg)

![](https://github.com/vivianxww/images/blob/master/NT6.jpg)

### 3、 查询便签

![](https://github.com/vivianxww/images/blob/master/XZ5CX1.jpg)

![](https://github.com/vivianxww/images/blob/master/CX2.jpg)

![](https://github.com/vivianxww/images/blob/master/CX3.jpg)

![](https://github.com/vivianxww/images/blob/master/CX4.jpg)

![](https://github.com/vivianxww/images/blob/master/CX5.jpg)

![](https://github.com/vivianxww/images/blob/master/CX6.jpg)

![](https://github.com/vivianxww/images/blob/master/CX7.jpg)

![](https://github.com/vivianxww/images/blob/master/CX8.jpg)

## 二、代码说明

### 1、时间戳

    使用getNowTime()函数得到当前时间。时间格式为2017-12-13 14：00：00 主要代码如下：
    public class GetTime {

    public static String getNowDate()
    {
        Date dt= new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());//24小时格式
        String time = sdf.format(dt);
        return time;
    }

### 2、模糊查询标题

   （1）NoteList的onOptionsItemSelected(MenuItem item) 方法中，新增加了menu_Search选项，并且调用customView()方法导入布局文件 主要代码如下：

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_add:
           startActivity(new Intent(Intent.ACTION_INSERT, getIntent().getData()));
           return true;

        case R.id.menu_paste:
          startActivity(new Intent(Intent.ACTION_PASTE, getIntent().getData()));
          return true;

        case R.id.menu_Search:
                //使用Dialog弹出对话框进行输入查询
                customView();//新增方法-引用布局文件
                return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }


   （2）新增方法customView()：用于导入布局文件R.layout.search_by_title作为弹出对话框的样式；同时根据用户选择的取消/确认进行事件响应；若选择确认，这使用Search()方法进行查询。 主要代码如下：

    public void customView( )
    {
        TableLayout search_view = (TableLayout)getLayoutInflater().inflate(
                R.layout.search_by_title,null
        );

    mText = (EditText) search_view.findViewById(R.id.SearchTitle);



        new AlertDialog.Builder(this)
                .setTitle("查询")
                .setView(search_view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //创建一个·Toast信息
                        Toast.makeText(NotesList.this,"查询成功！！",
                                //设置显示的时间
                                Toast.LENGTH_SHORT).show();
                        Search();


                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //创建一个·Toast信息
                        Toast.makeText(NotesList.this,"取消查询！！",
                                Toast.LENGTH_SHORT).show();

                    }
                })
                .create()
                .show();

    }

（3）Search()：用于偶去用户输入的查询关键字并且进行数据库的查询，返回mCursor，如果返回值非空，再调用refresh()刷新，重新加载当前页面List项。 主要代码如下：

    protected void Search() {

        mUri = NotePad.Notes.CONTENT_URI;

        String Title="";//Title初始化
        //点击EditTitle时

        if(  mText!=null )
            if(mText.getText() != null)
                Title = mText.getText().toString();//读取

        if( mText==null )System.out.println("mText 是 null！！");

        System.out.println("Title"+Title);

        //查询条件语句
        String selection =  NotePad.Notes.COLUMN_NAME_TITLE + " like ? ";
        //查询条件语句的条件值
        String[]   selectionArgs = {"%" + Title + "%"};
        System.out.println("AA!!!==" + selectionArgs[0]);
        System.out.println("MURI==" + mUri);

        mCursor=getContentResolver().query(
                mUri,    // The URI for the note to update.
                PROJECTION,  // The values map containing the columns to update and the values to use.
                selection,    // 查询条件
                selectionArgs, 
                null 
        );//查询数据完成

        if (mCursor != null) {

            System.out.println("刷新页面");
            refresh();
        }
        else
            System.out.println("刷新失败 mCursor==NULL");

    }

（4）refresh()：根据查询的结果mCursor，重新加载当前页面List项。 主要代码如下：

    public void refresh() {
        String[] dataColumns = { NotePad.Notes.COLUMN_NAME_TITLE,
                NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE} ;

        int[] viewIDs = { android.R.id.text1 ,android.R.id.text2};//显示的组件数量要等于dataColumns   一对一关系

        // 用SimpleCursorAdapter加载查询结果

        SimpleCursorAdapter adapter
                = new SimpleCursorAdapter(
                this,       
                R.layout.noteslist_item,          //表明列表项的布局
                mCursor,                           // 表示从查询得到的光标中的记录作为适配器的数据
                dataColumns,                      //显示的内容
                viewIDs                           //显示用的视图
        );
        setListAdapter(adapter);
    }


（5）修改布局文件 list_options_menu.xml：添加了查询选项。主要代码如下：

    <item android:id="@+id/menu_Search"
        android:icon="@android:drawable/ic_menu_search"
        android:title="Search"
        android:alphabeticShortcut='p'
        android:showAsAction="always" />

（6）新增布局文件search_by_title.xml：作为弹出的查询窗口的布局样式。 主要代码如下：

    
    <?xml version="1.0" encoding="utf-8"?>
    <TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:id="@+id/Tablelayout"
    android:paddingLeft="6dip"
    android:paddingRight="6dip"
    android:paddingBottom="3dip">

    <TableRow android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:baselineAligned="false">


        <!--用户输入框-->
        <EditText android:id="@+id/SearchTitle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="请输入标题查询"
            android:selectAllOnFocus="true"
            android:background="@android:color/darker_gray"
            android:textColorHint="@android:color/background_dark"
            android:textColor="@android:color/background_light"
            android:textColorHighlight="@android:color/black"
            android:textColorLink="@android:color/background_light"
            android:textSize="24sp" />

     </TableRow>
  
     </TableLayout>
