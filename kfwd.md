✅ CPR 训练软件开发文档（iOS 原生，Xcode + Swift）
一、项目概述
本项目为一款 单机版 iOS CPR 训练工具 App，通过 低功耗蓝牙（BLE） 同时连接最多 6 台 TLSR8266F512 芯片设备，实时采集学员操作数据（胸外按压 + 人工呼吸），提供反馈与训练结果展示。
* ✅ 无需网络与登录，打开即用
* ✅ 不保留训练历史记录
* ✅ 遵循 美国心脏协会（AHA）CPR 训练指南

二、训练选项功能
🧠 训练模式
* 30:2 模式（30 次按压 + 2 次人工呼吸）
* 仅按压模式
⏱ 训练时长
* 可选：1 / 2 / 3 / 5 分钟

三、核心功能模块规划
1. 训练设置模块
* 模式选择：使用 UISegmentedControl
* 时间选择：使用 UIPickerView 或 UIDatePicker（自定义分钟）
* 蓝牙设备状态总览：使用 UICollectionView 展示最多 6 台设备（CPR-01 ~ CPR-06）
* 点击「开始训练」按钮后跳转到实时数据页

2. BLE 蓝牙通信模块（支持 TLSR8266F512）
* 使用 CoreBluetooth 实现 BLE 功能
* 同时连接最多 6 台设备（设备广播名：CPR-01 ~ CPR-06）
* 每台设备使用如下 UUID 通讯：
类型	UUID
Service UUID	0xFFF0
Notify UUID	0xFFF1
Write UUID	0xFFF2
AT Command UUID	0xFFF3
	•	数据通信格式为标准 JSON，每 0.5 秒更新一次：
{
  "deviceId": "CPR-01",
  "compression": {
    "depth": 5.2,
    "rate": 110,
    "rhythm": "good"
  },
  "breath": {
    "count": 2,
    "volume": [550, 480]
  }
}
* 自动绑定设备编号与训练卡片
* 支持掉线重连（使用 didDisconnectPeripheral 回调 + 自动重试）
* 状态提示：连接中、已连接、准备中、已掉线等

3. 实时训练监控模块
* 每台设备显示一张卡片，使用 UICollectionView 横向滑动，或 UIPageViewController
* 每卡显示数据如下：
数据名称	显示形式	说明
按压深度	动态数字 + 颜色条	推荐 5–6 cm
按压频率	实时次数/分钟	推荐 100–120 次/分钟
按压节奏	状态图标（正常/快/慢）	根据节拍节奏
呼吸次数	实时计数	每轮呼吸数量
吹气量	柱状图（CorePlot 或 SwiftUI Chart）	推荐 500–600 ml
合格反馈	图标 + 提示文字	如“过浅”、“节奏不稳”等
4. 成绩展示模块（训练结束时）
* 使用 UICollectionView 或 UITableView 横向展示训练结果卡片
成绩项	描述
平均按压深度	单位 cm，是否达标
平均频率	次/分钟
吹气成功次数	合格呼吸次数
吹气平均量	单位 ml
综合评分	A/B/C 或 0–100
改进建议	文字建议（如“呼吸不足”）
四、技术架构设计
前端技术栈
项目	技术
UI 框架	UIKit / SwiftUI（建议 SwiftUI）
状态管理	使用 ObservableObject + @Published 模型（SwiftUI）或 MVC（UIKit）
图表展示	Swift Charts（iOS 16+）或 CorePlot
动画反馈	UIView.animate / SwiftUI 动画
BLE 通信	CoreBluetooth 原生框架
数据处理方式
* 每台设备每 0.5 秒接收一次 JSON 数据
* 使用 Timer.scheduledTimer 或 DispatchSourceTimer 定时处理刷新 UI
* 所有数据仅保留在内存中，训练结束即清空

五、界面结构设计建议（Figma 原型页）
📋 页面结构
1. 首页 / 设置页
    * 模式选择（30:2 / 仅按压）
    * 时间选择（1 / 2 / 3 / 5 分钟）
    * 蓝牙设备状态（编号 + 是否已连接）
    * 开始训练按钮
2. 训练页
    * 横滑卡片视图（CPR-01 ~ CPR-06）
    * 实时反馈（动态图 + 数值）
    * 数据可视化图表
3. 成绩页
    * 每台设备的成绩卡
    * 分数 + 反馈建议
    * 返回首页按钮

六、其他说明
* ✅ 无需注册/登录，默认账户：admin / password（本地模拟）
* ✅ 本地运行，无需数据库或网络连接
* ✅ 所有训练数据仅保存在当前训练轮中
* ✅ 默认组织名称为：Actnow Training Centre
* ✅ 蓝牙芯片为 TLSR8266F512，广播名为 CPR-01 ~ CPR-06

七、后续可拓展方向（建议预留接口）
* 支持通过 AT 指令进行 设备 OTA 固件升级（使用 WriteCharacteristic 写入指令）
