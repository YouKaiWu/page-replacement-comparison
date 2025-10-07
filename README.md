# Algorithm-comparison
- 比較四種 Page Replacement 演算法(FIFO, ARB, Optimal, Custom)的差異

## 架構

src/main/java/com/example/
├── model/                              # 資料模型
│   └── ReferenceString.java            # page reference 序列
│
├── generator/                          # 產生測試資料
│   ├── ReferenceStringGenerator.java   # 抽象類，定義產生器的基本介面與行為
│   ├── RandomGenerator.java        
│   ├── LocalityGenerator.java          
│   └── CustomGenerator.java            # 產生高頻率(90%)的 hotPages，與低頻率的 coldPages
│
├── algorithm/                          # Page Replacement 演算法
│   ├── Algorithm.java                  # 抽象類，定義演算法的基本介面與行為
│   ├── FIFO.java
│   ├── ARB.java
│   ├── Optimal.java
│   └── Custom.java                     
│
├── result/                             # 統計與輸出
│   ├── Data.java                       # 儲存一次模擬的結果 (page faults, disk write, interrupt)
│   ├── Result.java                     # 儲存 Data + generator 名稱 + algorithm 名稱
│   └── ResultExporter.java             # 負責格式化、輸出比較結果
│
├── runner/
│   └── AlgorithmRunner.java            # 統一調用 generator + algorithm，跑實驗
│
└── Main.java                           # 程式進入點

## 環境

### OS
- Windows 11

### Java
- 版本：Java 24.0.2 (2025-07-15)

### Maven
- 版本：Apache Maven 3.9.11


### 執行方式
- note: 請使用 Bash 執行腳本、請確保 `run.sh` 有執行權限
- 在專案根目錄下執行以下指令：
`./run.sh` or `sh run.sh`
    - 執行 log 存在 run.log
