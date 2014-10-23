concurrnt-test
==============


一、Lock、RWLock、RWFairLock、Sync、Atomic、LongAdder、Stamped测试Counter读写性能。
1、1 read, 1 write
  {Stamped=2520, LongAdder=1247, Atomic=1067, RWLock=2837, Sync=2912, Lock=2316}
  
2、4 read, 1 write
  {Stamped=6754, LongAdder=600, Atomic=2381, RWLock=4167, Sync=5296, Lock=3737}

3、4 read, 4 write
  {Stamped=6424, LongAdder=674, Atomic=2876, RWLock=4044, Sync=5563, Lock=3787}
  
4、8 read, 1 write
  {Stamped=3889, LongAdder=656, Atomic=2141, RWLock=4156, Sync=5743, Lock=3746}
5、16 read, 1 write
  {Stamped=4121, LongAdder=644, Atomic=2804, RWLock=4038, Sync=5276, Lock=3798}
