package Entry;

import Exception.*;
import Runtime.*;

import java.util.*;

@SuppressWarnings("all")
public class BattleEntry {
    //记录全部策略对战时的分数
    private static Map<String, Integer> pointCounter = new HashMap<>();

    //全部策略进行对战
    public static void battle(Runner runner, int round) throws RunningException{
        //初始化分数为0分
        for(String key: GlobalValue.getPrograms().keySet()){
            pointCounter.put(key, 0);
        }
        System.out.println("\nevery battle result:");
        //进行策略间的两两对战
        for(String key1: GlobalValue.getPrograms().keySet()){
            for (String key2: GlobalValue.getPrograms().keySet()){
                if(!key1.equals(key2)){
                    battle(runner, key1, key2, round, true);
                }
            }
        }
        //Map.Entry<String,Integer>是一个泛型，表示Entry里的key和value
        //map.entrySet（）是将map里的每一个键值对取出来封装成一个Entry对象在存到一个Set里面
        List<Map.Entry<String,Integer>> list = new ArrayList<>(pointCounter.entrySet());
        //Comparator 排序
        list.sort((e1,e2)->e2.getValue()-e1.getValue());
        System.out.println("\nfinal battle result:");
        list.forEach(System.out::println);
    }

    //进行两个策略的对战
    //s1:策略一  s2:策略二  round: 对战轮数  record = false 两个策略对战  record = true 多个策略两两对战
    public static void battle(Runner runner, String s1, String s2, int round, boolean record) throws RunningException {
        //分别保存两个策略最新一轮的结果
        Integer result1;
        Integer result2;
        //分别保存两个策略对战的分数
        int point1 = 0;
        int point2 = 0;
        //清空globalval
        GlobalValue.clearGlobalVal();
        //分别保存两个策略每轮对战的结果的历史数据
        List<Integer> history1 = new ArrayList<>();
        //add(0):
        history1.add(0);
        List<Integer> history2 = new ArrayList<>();
        history2.add(0);
        //globalval添加两个值保存双方对战的历史结果
        GlobalValue.setGlobalVal("history1", history1);
        GlobalValue.setGlobalVal("history2", history2);
        //record:false
        if(!record)System.out.println("\nresult:\t\t" + s1 + "\t" + s2);
        //进行多轮对战
        for(int i=0;i<round;i++){
            //FIRST_RUN = 1
            result1 = runner.run(s1, Program.FIRST_RUN);
            history1.add(result1);
            //SECOND_RUN = 2
            result2 = runner.run(s2, Program.SECOND_RUN);
            history2.add(result2);
            if(result1 == null) throw new RunningException("first-run program has no return");
            if(result2 == null) throw new RunningException("second-run program has no return");
            //结果只有 1 和 0 两种
            if(result1 != 1 && result1 != 0)
                throw new RunningException("the first-run program's return value is invaild for battle" +
                        "(it can only be 0 or 1)");
            if(result2 != 1 && result2 != 0)
                throw new RunningException("the second-run program's return value is invaild for battle" +
                        "(it can only be 0 or 1)");
            //根据得分规则来加上相应的分数
            if(result1==0 && result2==0){
                point1 += 1;
                point2 += 1;
            }
            if(result1==1 && result2==0){
                point2 += 5;
            }
            if(result1==0 && result2==1){
                point1 += 5;
            }
            if(result1==1 && result2==1){
                point1 += 3;
                point2 += 3;
            }
            //record:false
            if(!record)System.out.println("round " + (i+1) + ":\t" + result1 + "\t" + result2);
        }
        //record:true
        if(record){
            System.out.println(s1 + " vs " + s2 + ": " + point1 + "\t"+ point2);
            //更新比分(累加比分)
            pointCounter.put(s1, pointCounter.get(s1) + point1);
            pointCounter.put(s2, pointCounter.get(s2) + point2);
        }
        //record:false
        else System.out.println("\nfinal:\t" + s1 + "=" + point1 + "   " + s2 + "=" + point2);
    }

}
