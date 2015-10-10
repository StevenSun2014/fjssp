clear;  
n_makespan=160;%makespan  
n_bay_nb=10;%total bays  
n_task_nb = 10;%total tasks  
n_start_time=[13 104 89 1   5   54  43  91  95  1];%start time of every task  
n_duration_time =[41    19  6   12  37  34  48  10  56  3];%duration time of every task  
n_bay_start=[2  10  3   2   6   2   7   7   3   5]; %bay id of every task  
rec=[0,0,0,0];%temp data space for every rectangle  
for i =1:n_task_nb  
  rec(1) = n_start_time(i);  
  rec(2) = n_bay_start(i);  
  rec(3) = n_duration_time(i);  
  rec(4) = 1;  
   rectangle('Position',rec,'LineWidth',0.5,'LineStyle','-','FaceColor','g');%draw every rectangle  
   text((n_start_time(i)+n_duration_time(i)/3),(n_bay_start(i)+0.5),['t',int2str(i)]);%label the id of every task  
end  