# Algorithm-comparison
- 比較四種 Page Replacement 演算法(FIFO, ARB, Optimal, Custom)的差異

## 專案結構說明

### model
#### ReferenceString.java
存放 page reference 序列。

---

### generator
#### ReferenceStringGenerator.java
抽象類，定義產生器的基本介面與行為，用來生成 page reference 序列。

#### RandomGenerator.java
隨機生成 page reference 序列。

#### LocalityGenerator.java
生成具有區域性特徵的 page reference 序列。

#### CustomGenerator.java
生成高頻率 (90%) 的 hotPages 與低頻率的 coldPages。

---

### algorithm
#### Algorithm.java
抽象類，定義 Page Replacement 演算法的基本介面與行為。

#### FIFO.java
First-In-First-Out 實作。

#### ARB.java
Additional-reference-bits 實作。

#### Optimal.java
Optimal 實作，理論上最低的 page fault。

#### Custom.java
自定義 Page Replacement 演算法，freq + time decay。

---

### result
#### Data.java
儲存一次模擬的結果，包括 page faults、disk write、interrupt。

#### Result.java
儲存 Data、generator 名稱與 algorithm 名稱，方便比較。

#### ResultExporter.java
負責將比較結果格式化並輸出成 excel 表格。

---

### runner
#### AlgorithmRunner.java
統一調用 generator 與 algorithm，執行實驗。

---

### Main.java
專案進入點。


---
## 環境

### OS
- Windows 11

### Java
- 版本：Java 24.0.2 (2025-07-15)

### Maven
- 版本：Apache Maven 3.9.11


---
### 執行方式
- note: 請使用 Bash 執行腳本、請確保 `run.sh` 有執行權限
- 在專案根目錄下執行以下指令：
`./run.sh` or `sh run.sh`
    - 執行的 log 檔存在 `run.log`
