package jigsawpuzzle;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

public class jigsawPuzze extends JFrame {
    
        //选择关卡
        int mode;
        //行
        int Row;
        //列
        int Column;
        //标签，存放图片，用于拼图时作对比
	private JLabel modelLabel;
        //画板，存放拼图原图
	private JPanel centerPanel;
        //空按钮，保存空的那一格图片
	private JButton emptyButton;
        //展示步数
        private JButton showButton;
        //用序号来进行图片选择
	int num = 0;
        //计算游戏步数
        int count=0;
        //计时
        long time;
        
        public static long usedTime = 0; // 玩家用时
        public static Timer userTimeAction;
        
        
	//建立窗口构造方法
	public jigsawPuzze(int mo,int R,int C) {
		super();
                
                mode=mo;
                Row=R;
                Column=C;
                //设置此 frame 是否可由用户调整大小，true：可调节，false：不可调节
		setResizable(false);
		//设置标题
                setTitle("拼图游戏");
                //界面大小
		setBounds(500, 100, 370, 625);
                //设置可关闭
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
		//创建面板对象，并增加边框、布局
		final JPanel topPanel = new JPanel();
		topPanel.setBorder(new TitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		topPanel.setLayout(new BorderLayout());
		getContentPane().add(topPanel, BorderLayout.NORTH);//放于上方
                
		//创建标签放原图
		modelLabel = new JLabel();
                //选择一张图片
		modelLabel.setIcon(new ImageIcon("image"+mode+"/"+ num+ "model.jpg"));
		topPanel.add(modelLabel, BorderLayout.WEST);
                
		//在右侧加个面板，添加两个按钮
		JPanel eastPanel = new JPanel();
		topPanel.add(eastPanel,BorderLayout.CENTER);
		eastPanel.setLayout(new BorderLayout());
                
		JButton nextButton = new JButton();
		nextButton.setText("下一张拼图");
		nextButton.addActionListener(new NextButtonAction());
		eastPanel.add(nextButton,BorderLayout.NORTH);
                
                JButton overButton = new JButton();
		overButton.setText("结束本轮游戏");
		overButton.addActionListener(new OverButtonAction());
		eastPanel.add(overButton,BorderLayout.SOUTH);

                showButton = new JButton();
		showButton.setText("步数:"+count);
		eastPanel.add(showButton,BorderLayout.EAST);
                
		//创建按钮开局添加监听
		final JButton startButton = new JButton();
		startButton.setText("打乱拼图顺序");
                //注册监听器
		startButton.addActionListener(new StartButtonAction());
		eastPanel.add(startButton, BorderLayout.CENTER);
                
		//初始化中心面板，设置边框，添加按钮
		centerPanel = new JPanel();
		centerPanel.setBorder(new TitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		centerPanel.setLayout(new GridLayout(Row, Column));
		getContentPane().add(centerPanel, BorderLayout.CENTER);
                
                JPanel timePanel = new JPanel();
                timePanel.setBounds(500, 100, 370, 200);
                addPanelTime(timePanel);
                getContentPane().add(timePanel, BorderLayout.SOUTH);//放于上方
                
		//初始化图片，获得图片初始排序
		String[][] originalOrder = order();
		//按排列添加按钮，设置图片
		for (int row = 0; row < Row; row++) {
			for (int col = 0; col < Column; col++) {
				final JButton button = new JButton();
                                //设置按钮的名称，行号+列号代表按钮的名称
				button.setName(row+""+col);
                                //设置为要显示的图片
				button.setIcon(new ImageIcon(originalOrder[row][col]));
                                //第0张为空，用来移位
				if (originalOrder[row][col].equals("image"+mode+"/"+ num+"00.jpg"))
					emptyButton = button;
                                //为按钮注册监听器
				button.addActionListener(new ImgButtonAction());
                                //将当前获得的图片按钮加入画板中
				centerPanel.add(button);
			}
		}
                time=System.nanoTime();//记录时间
	}
        
        
	//初始化图片
	private String[][] order() {
                //一开始的排序就是图片原始排序
		String[][] originalOrder = new String[Row][Column];
		for (int row=0; row < Row; row++) {
			for (int col=0; col < Column; col++) {
				originalOrder[row][col] = "image"+mode+"/"+ num+ row+ col+ ".jpg"; 
			}
		}
		return originalOrder;
	}
        
        
	//随机排列图片
	private String[][] reorder() {
                //原始排序
		String[][] originalOrder = new String[Row][Column];
		for (int row=0; row < Row; row++) {
			for (int col=0; col < Column; col++) {
				originalOrder[row][col] = "image"+mode+"/" + num + row + col + ".jpg"; 
			}
		}
                //打乱后的排序
		String[][] randomOrder = new String[Row][Column];
		for (int row=0; row < Row; row++) {
			for (int col=0; col < Column; col++) {
				while (randomOrder[row][col]==null) {
					int r = (int) (Math.random()*Row);
					int c = (int) (Math.random()*Column);
					if (originalOrder[r][c] != null) {
						randomOrder[row][col] = originalOrder[r][c];
						originalOrder[r][c] = null;
					}
				}
			}
		}
		return randomOrder;
	}
        
        
        //开始游戏，打乱图片排序
	class StartButtonAction implements ActionListener {
                //在监听器内实现响应功能
		public void actionPerformed(ActionEvent e) {
                        
                        //重新计数
                        count=0;
                        time=System.nanoTime();//记录时间
                        showButton.setLabel("步数："+count);
			String[][] randomOrder = reorder();
                        //用来逐个获得画板上的按钮
			int i= 0;
			for (int row=0; row < Row; row++) {
				for (int col=0; col < Column; col++) {
                                        //getComponent(int n)获得此容器中的第 n 个组件
					JButton button = (JButton) centerPanel.getComponent(i);
                                        //获得下一个组件（按钮）
                                        i++;
                                        //设置为要显示的图片
					button.setIcon(new ImageIcon(randomOrder[row][col]));
                                        //初始时设置第0格为空按钮
					if(randomOrder[row][col].equals("image"+mode+"/"+ num+ "00.jpg"))
						emptyButton=button;
				}
			}
		}
	}
        
        
	//监听器，用于对游戏时点击按钮进行排列图片
	class ImgButtonAction implements ActionListener {
            
		public void actionPerformed(ActionEvent e) {
                        //保存空按钮的名称
			String emptyName= emptyButton.getName();
                        //charAt(int index)返回指定索引处的 char 值
                        //0号位置保存行号，1号位置保存列号
                        //获得空按钮的行列号
			char emptyRow = emptyName.charAt(0);
			char emptyCol = emptyName.charAt(1);
                        //保存点击的按钮
			JButton clickButton = (JButton) e.getSource();
                        //获得点击的按钮的名称
			String clickName = clickButton.getName();
                        //获得点击的按钮的行列号
			char clickRow = clickName.charAt(0);
			char clickCol = clickName.charAt(1);
                        //abs()返回绝对值
                        //可以和空格交换的按钮总是和空格同行或者同列的，即行的差加上列的差总是1
			if (Math.abs(clickRow - emptyRow) + Math.abs(clickCol - emptyCol) == 1) {
                                System.out.println("第"+emptyButton.getName().charAt(0)+"行 第"+emptyButton.getName().charAt(1)+"列");
                                //步数累计
                                count++;
                                //更新步数的按钮
                                showButton.setLabel("步数："+count);
                                //将空按钮填上点击的按钮的图片
				emptyButton.setIcon(clickButton.getIcon());
                                //用被点击的按钮保留没有显示的图片块
				clickButton.setIcon(new ImageIcon("image"+mode+"/"+ num+ "00.jpg"));
                                //点击的按钮成为新的空按钮
				emptyButton = clickButton;
                                
                                //判断是否可以结束游戏
                                int i=0;
                                boolean flag=true;
                                for (int row=0; row < Row; row++) {
                                    for (int col=0; col < Column; col++) {
                                        //getComponent(int n)获得此容器中的第 n 个组件
					JButton button = (JButton) centerPanel.getComponent(i);
                                        //获得下一个组件（按钮）
                                        i++;
                                        //Icon getIcon() 返回该标签显示的图形图像（字形、图标）
                                        //System.out.print(button.getIcon().toString()+" ");
                                        //System.out.print("image4/"+num+row+col+".jpg ");
                                        if(!button.getIcon().toString().equals("image"+mode+"/"+num+row+col+".jpg"))
                                        {
                                            flag=false;
                                            break;
                                        }
                                    }
                                    if(flag==false)break;
                                }
                                if(flag==true)
                                {
                                    String str="成功！拼图正确！"+"\n"+"使用步数："+count+"\n"+"耗时（单位：纳秒）："+(System.nanoTime()-time);
                                    JOptionPane.showMessageDialog(null,str);
                                }
                                //System.out.println(count);
			}
		}
	}
        
        
	//换下一张图片
	class NextButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
                        //重新计数
                        count=0;
                        time=System.nanoTime();//记录时间
                        showButton.setLabel("步数："+count);
                        //取下一张图片
			if (num==3) num=0;
                        else ++num;
                        //将新的图片设置为新的标签（做对照用）
			modelLabel.setIcon(new ImageIcon("image"+mode+"/"+num+"model.jpg"));
                        //获得图片初始排序
			String[][] originalOrder = order();
                        //用来逐个获得画板上的按钮
			int i= 0;
			for (int row=0; row < Row; row++) {
				for (int col=0; col < Column; col++) {
                                        //getComponent(int n)获得此容器中的第 n 个组件
					JButton button = (JButton)centerPanel.getComponent(i);
                                        //获得下一个组件（按钮）
                                        i++;
                                        //设置为要显示的图片
					button.setIcon(new ImageIcon(originalOrder[row][col]));
                                        //初始时设置第0格为空按钮
					if(originalOrder[row][col].equals("image"+mode+"/"+ num+ "00.jpg"))
						emptyButton=button;
				}
			}
		}
	}
        
        //结束游戏
        class OverButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
                    dispose();
                }
	}
        
        private void addPanelTime(JPanel panelComponent) {
            
            JPanel panelTime = new JPanel();
            panelTime.setBorder(new TitledBorder("时间"));
            panelTime.setLayout(new GridLayout(2, 1));

            final JLabel lbSysTime = new JLabel();
            final JLabel lbUserTime = new JLabel();

            panelTime.add(lbSysTime, BorderLayout.NORTH);
            panelTime.add(lbUserTime, BorderLayout.SOUTH);

            // 设置系统时间定时器
            Timer sysTimeAction = new Timer(500, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    long timeMillis = System.currentTimeMillis();
                    SimpleDateFormat df = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");
                    lbSysTime.setText("    系统时间：  " + df.format(timeMillis));
                }
            });
            sysTimeAction.start();
            userTimeAction = new Timer(1000, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    lbUserTime.setText("    您已用时：  " + (++usedTime)+ " sec.");
                }
            });
            userTimeAction.start();

            panelComponent.add(panelTime, BorderLayout.EAST);

    }
}