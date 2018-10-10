package Ant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Room extends JFrame
{
    private static final long serialVersionUID = 1L;// serialVersionUID唯一的可串行化的版本
    private static final int incTime = 200;//每隔incTime刷新一次界面，单位毫秒
    private static int currentSituation;//当前情况，0-31
    private static int totalSituation;//总情况数
    private Game game = null;//游戏类
    //四个初始条件
    private int length;
    private int[] originPosition;
    private boolean[] originDirection;
    private static final int antNumber = 5;
    private static final int[] speed = {5, 5, 5, 5, 5, 5};
    //最大最小值
    private int minTime;
    private int maxTime;

    private static boolean pause = true;//true为暂停，false为播放
    private static int speedMultiplier = 1;//播放速度，1或5

    private Paintpanel paintpanel = null;//画图板
    private Controlpanel controlpanel = null;//控制板

    private ImageIcon[][] antIcon = new ImageIcon[6][2];//蚂蚁图标，0向左1向右
    //图标和按钮大小
    private static final int iconHeight = 30;
    private static final int iconWidth = 40;
    private static final int buttonBoarder = 70;


    public Room()
    {
        //生成界面
        super("Ant Game");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1600, 550);
        this.setLayout(new GridLayout(2, 1));
        this.setLocationRelativeTo(null);

        //蚂蚁图标
        antIcon[0][0] = new ImageIcon(getClass().getResource("/Icon/ant-0.png"));
        antIcon[1][0] = new ImageIcon(getClass().getResource("/Icon/ant-1.png"));
        antIcon[2][0] = new ImageIcon(getClass().getResource("/Icon/ant-2.png"));
        antIcon[3][0] = new ImageIcon(getClass().getResource("/Icon/ant-3.png"));
        antIcon[4][0] = new ImageIcon(getClass().getResource("/Icon/ant-4.png"));
        antIcon[5][0] = new ImageIcon(getClass().getResource("/Icon/ant-5.png"));
        antIcon[0][1] = new ImageIcon(getClass().getResource("/Icon/ant-0-right.png"));
        antIcon[1][1] = new ImageIcon(getClass().getResource("/Icon/ant-1-right.png"));
        antIcon[2][1] = new ImageIcon(getClass().getResource("/Icon/ant-2-right.png"));
        antIcon[3][1] = new ImageIcon(getClass().getResource("/Icon/ant-3-right.png"));
        antIcon[4][1] = new ImageIcon(getClass().getResource("/Icon/ant-4-right.png"));
        antIcon[5][1] = new ImageIcon(getClass().getResource("/Icon/ant-5-right.png"));

        //初始化
        initialization();

        //初始化控制面板和画图面板
        controlpanel = new Controlpanel();
        paintpanel = new Paintpanel();
        controlpanel.setBackground(Color.white);
        paintpanel.setBackground(Color.white);
        this.getContentPane().add(controlpanel);
        this.getContentPane().add(paintpanel);
        this.setVisible(true);

    }

    public void initialization()
    {
        //初始化总情况数，当前情况设为0，初始化最大最小值和蚂蚁的初始条件，生成第一种游戏
        currentSituation = 0;
        totalSituation = 1;
        for(int i = 0; i < antNumber; i++)
        {
            totalSituation *= 2;
        }
        minTime = 9999;
        maxTime = 0;
        length = 300;
        int a[] = {30, 80, 110, 160, 250};
        boolean b[] = {false, false, false, false, false};
        originPosition = a;
        originDirection = b;
        game = new Game(length, antNumber, originPosition, originDirection, speed);
    }

    public int playGame()
    {
        //根据currentSituation的值确定并生成游戏，同时返回该种情况下蚂蚁爬行的总时间，单位毫秒
        originDirection = new boolean[antNumber];
        for(int j = 0; j < antNumber; j++)
        {
            originDirection[antNumber-1-j] = (currentSituation & (0x01 << j)) != 0;
        }
        game = new Game(length,antNumber,originPosition,originDirection,speed);
        return game.getTotalTime();
    }

    public class Controlpanel extends JPanel implements ActionListener
    {
        //初始化按钮，加载图片
        private static final long serialVersionUID = 1L;
        private ImageIcon play_icon = new ImageIcon(getClass().getResource("/Icon/play.png"));
        private ImageIcon pause_icon = new ImageIcon(getClass().getResource("/Icon/pause.png"));
        private ImageIcon replay_icon = new ImageIcon(getClass().getResource("/Icon/replay.png"));
        private ImageIcon next_icon = new ImageIcon(getClass().getResource("/Icon/next.png"));
        private ImageIcon slow_icon = new ImageIcon(getClass().getResource("/Icon/slow.png"));
        private ImageIcon fast_icon = new ImageIcon(getClass().getResource("/Icon/fast.png"));
        private ImageIcon begin_icon = new ImageIcon(getClass().getResource("/Icon/begin.png"));
        private ImageIcon end_icon = new ImageIcon(getClass().getResource("/Icon/end.png"));
        private JButton play_button = new JButton(play_icon);
        private JButton pause_button = new JButton(pause_icon);
        private JButton replay_button = new JButton(replay_icon);
        private JButton next_button = new JButton(next_icon);
        private JButton slow_button = new JButton(slow_icon);
        private JButton fast_button = new JButton(fast_icon);
        private JButton begin_button = new JButton(begin_icon);
        private JButton end_button = new JButton(end_icon);


        public Controlpanel()
        {
            this.setLayout(null);

            //设置按钮位置
            play_button.setBounds(90, 80, buttonBoarder, buttonBoarder);
            pause_button.setBounds(180, 80, buttonBoarder, buttonBoarder);
            replay_button.setBounds(270, 80, buttonBoarder, buttonBoarder);
            next_button.setBounds(360, 80, buttonBoarder, buttonBoarder);
            slow_button.setBounds(90, 170, buttonBoarder, buttonBoarder);
            fast_button.setBounds(180, 170, buttonBoarder, buttonBoarder);
            begin_button.setBounds(270, 170, buttonBoarder, buttonBoarder);
            end_button.setBounds(360, 170, buttonBoarder, buttonBoarder);

            //设置鼠标停在按钮上是的提示文字
            play_button.setToolTipText("播放");
            pause_button.setToolTipText("暂停");
            replay_button.setToolTipText("重播此种情况");
            next_button.setToolTipText("下一种情况");
            slow_button.setToolTipText("1倍速播放");
            fast_button.setToolTipText("5倍速播放");
            begin_button.setToolTipText("从头开始");
            end_button.setToolTipText("跳过所有");

            //增加监听器，以相应点击事件
            play_button.addActionListener(this);
            pause_button.addActionListener(this);
            replay_button.addActionListener(this);
            next_button.addActionListener(this);
            slow_button.addActionListener(this);
            fast_button.addActionListener(this);
            begin_button.addActionListener(this);
            end_button.addActionListener(this);

            //按钮加入面板
            this.add(play_button);
            this.add(pause_button);
            this.add(replay_button);
            this.add(next_button);
            this.add(slow_button);
            this.add(fast_button);
            this.add(begin_button);
            this.add(end_button);
        }

        @Override
        public void paint(Graphics g)
        {
            //重写paint函数
            super.paint(g);//清空原来的
            //画表格，显示当前状态
            Ant[] ants = game.getAnts();
            g.setColor(Color.black);
            g.setFont(new Font("宋体", 1, 25));
            g.drawString("初始位置 初始方向 速度 现在位置 现在/掉落方向", 550, 50);
            g.drawString("当前用时：" + (double) game.getCurrentTime() / 1000, 1150, 120);
            g.drawString("此次用时：" + game.getTotalTime() / 1000, 1150, 155);
            g.drawString("当前最小用时：" + minTime, 1150, 190);
            g.drawString("当前最大用时：" + maxTime, 1150, 225);
            for (int i = 0; i < antNumber; i++)
            {
                g.drawImage(antIcon[i][1].getImage(), 500, 65 + 35 * i, iconWidth, iconHeight, this);
                g.drawString("  " + originPosition[i] + "  ", 550, 95 + 35 * i);
                g.drawString("  " + speed[i] + "  ", 775, 95 + 35 * i);
                g.drawString("  " + (ants[i].isOnWood() ? ants[i].getPosition() : "--"), 850, 95 + 35 * i);
                if(originDirection[i])
                {
                    g.setColor(new Color(Integer.decode("#3903f3")));
                }
                g.drawString("  " + (originDirection[i] ? "→" : "←") + "  ", 670, 95 + 35 * i);
                if(ants[i].isDirection())
                {
                    g.setColor(new Color(Integer.decode("#3903f3")));
                }else
                {
                    g.setColor(Color.black);
                }
                g.drawString("  " + (ants[i].isDirection() ? "→" : "←"), 1000, 95 + 35 * i);
                g.setColor(Color.black);
            }
            g.setColor(Color.red);
            g.setFont(new Font("宋体", 1, 25));
            g.drawString((pause ? "暂停" : "播放") + "中  " + speedMultiplier + "倍速", 180, 50);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            //响应按钮点击事件
            if (e.getSource() == play_button)
            {
                //播放，令暂停为false
                pause = false;
            }
            if (e.getSource() == pause_button)
            {
                //暂停，令暂停为true
                pause = true;
            }
            if (e.getSource() == replay_button)
            {
                //重播此种情况，令该game初始化
                game.initialization();
            }
            if (e.getSource() == next_button)
            {
                //下一种情况，当不是最后一种情况时，先计算该种情况的时间，将其与最大最小值比较，然后开始下一种情况并设置为播放
                if(currentSituation < totalSituation - 1)
                {
                    int time = playGame()/1000;
                    currentSituation++;
                    System.out.println(currentSituation + " : " + time);
                    if (time > maxTime)
                    {
                        maxTime = time;
                    }
                    if (time < minTime)
                    {
                        minTime = time;
                    }
                    playGame();
                    pause = false;
                }
            }
            if (e.getSource() == slow_button)
            {
                //慢速，播放速度设置为1
                speedMultiplier = 1;
            }
            if (e.getSource() == fast_button)
            {
                //快速，播放速度设置为5
                speedMultiplier = 5;
            }
            if (e.getSource() == begin_button)
            {
                //开始，令房间初始化
                initialization();
            }
            if (e.getSource() == end_button)
            {
                //结束，将游戏多次跳过，跳至最后一种情况，每次跳过都计算和比较时间
                while (currentSituation < totalSituation-1)
                {
                    int time = playGame()/1000;
                    currentSituation++;
                    System.out.println(currentSituation + " : " + time);
                    if (time > maxTime)
                    {
                        maxTime = time;
                    }
                    if (time < minTime)
                    {
                        minTime = time;
                    }
                    playGame();
                }
            }
        }
    }

    public class Paintpanel extends JPanel implements ActionListener
    {
        private static final long serialVersionUID = 1L;
        //计时器
        private Timer timer = null;

        public Paintpanel()
        {
            timer = new Timer(incTime, this);
            start();
        }

        public void start()
        {
            timer.restart();
        }

        public void paint(Graphics g)
        {
            super.paint(g);// 擦掉原来的
            //画杆子和蚂蚁
            g.setColor(Color.black);
            g.fillRect(40, 120, 1500, 3);
            Ant[] ants = game.getAnts();
            for (int i = 0; i < antNumber; i++)
            {
                if (ants[i].isOnWood())
                {
                    if (ants[i].isDirection())
                    {
                        g.drawImage(antIcon[ants[i].getNumber()][1].getImage(), 40 + 5 * ants[i].getPosition() - iconWidth/2, 120 - iconHeight, iconWidth, iconHeight, this);
                    } else
                    {
                        g.drawImage(antIcon[ants[i].getNumber()][0].getImage(), 40 + 5 * ants[i].getPosition()-iconWidth/2, 120 - iconHeight, iconWidth, iconHeight, this);
                    }
                }
            }
            //标注现在为第几种情况
            g.setColor(Color.red);
            g.setFont(new Font("宋体", 1, 25));
            g.drawString("正在播放第" + (currentSituation+1)+ "种情况(共"+totalSituation+"种情况)", 610, 180);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            //每隔0.2秒刷新一次界面
            controlpanel.repaint();
            this.repaint();
            //如果在播放，则令游戏模拟1或5次，每次令蚂蚁爬0.2秒
            if (!pause)
            {
                game.simulation(speedMultiplier, incTime);
            }
            //如果当前情况结束，则比较最小最大值
            if (game.getCurrentTime() >= game.getTotalTime())
            {
                int time = game.getTotalTime() / 1000;
                System.out.println(currentSituation + " : " + time);
                if (time > maxTime)
                {
                    maxTime = time;
                }
                if (time < minTime)
                {
                    minTime = time;
                }
            }
        }
    }

    public static boolean isPause()
    {
        return pause;
    }

    public static void setPause(boolean pause)
    {
        Room.pause = pause;
    }

    public static void main(String[] args)
    {
        //程序入口
        Room room = new Room();
    }
}
