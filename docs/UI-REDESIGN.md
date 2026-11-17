# WebHomeTV 「夜航」UI 重构说明

> 目标：**只换表现层，功能一个不少**。所有业务逻辑、Jar/Spider 运行、播放内核（EXO / IJK / MPV）、
> 接口、插件、WebHome SDK、同步、直播、遥控器焦点链路均未改动，一行 Java 业务代码都没碰。
> 本次唯一改到的 Java 文件是 `theme/ThemeTokens.java`（调色板常量表）与一处对应的单测期望。

## 1. 视觉方向

| 维度 | 改造前 | 改造后（夜航 / Nocturne） |
| --- | --- | --- |
| 主色 | 系统蓝 `#0B57D0` | 琥珀金 `#E9B872`（深色表）/ `#8A5A12`（浅色表） |
| 底色 | 中性灰蓝 | 深墨 `#0E1013` / 暖纸白 `#FAF7F1` |
| 圆角 | 4 / 8 / 12 / 16dp | 6 / 10 / 14 / 18 / 26dp |
| 卡片 | 8dp 圆角 + 40% 实心黑信息带 | 14dp 圆角 + 自下而上渐隐信息带 |
| 芯片 | 裸文字 / 灰底 | 胶囊芯片，选中琥珀容器实底 + 容器前景字 |
| 底部导航 | 56dp 透明贴底 | 64dp 上部圆角承托面 + 图标文字双态着色 |
| 播放控制层 | 整屏 20% 黑 + 裸文字动作条 | 上下渐隐 scrim + 玻璃胶囊动作芯片 |
| 焦点环 | 1.5dp | 2dp，暖白金 |

设计原则：所有色值走语义 token；两端（浅/深）成对定义；不允许在布局里出现裸色值。

## 2. 改动分层

### 2.1 设计 token 层（影响全站，包括未单独改动的页面）

- `values/webhtv_tokens.xml`、`values-night/webhtv_tokens.xml`：重写 49 个 Material 角色 + 非角色色
  的取值，**角色名与数量一个不动**（`ThemeContractTest` 用组件名推断资源名并冻结条目数）。
  新增 10 个叠加层语义色（`webhtv_scrim_*`、`webhtv_card_scrim_*`、`webhtv_hero_fade*`、
  `webhtv_control_glass`、`webhtv_badge_neutral`），避免任何 drawable 出现裸 hex。
- `theme/ThemeTokens.java`：`light()` / `dark()` 两张表逐槽位同步（已用脚本比对 XML 与 Java，
  53 个颜色槽全部一致，2 个浮点槽不变）。
- `values/webhtv_shapes.xml`：圆角体系整体放大约一档。
- `values/webhtv_dimens.xml`：新增 `webhtv_space_7`、`webhtv_size_gutter`、`webhtv_size_icon_large`、
  `webhtv_size_chip_height`、`webhtv_size_nav_height`、`webhtv_size_nav_inset`；卡片圆角 8→14 / 12→18；焦点环 1.5→2dp。
- `values/webhtv_type.xml`：排印层级重做（Label 11sp / Body 14-16sp / Title 21sp 中粗 / Headline 26sp 粗），
  全部改用 `sans-serif-medium` 字族，避开 `android:textFontWeight`（API 28 专属会触发 `NewApi` lint）。

### 2.2 组件化改造（改一处、全站受益）

- `styles.xml` 的 `Vod.*`：海报圆角 14dp（`Vod.Grid` / `.Grid.Large` / `.List`），与 `shape_vod_name`
  的 14dp 下圆角严格同值。
- `styles.xml` 的 `Control`：播放页动作条从「裸文字」升级为胶囊芯片（34dp 高、20% 白玻璃底、
  选中主色容器实底、文字随选中切色）。
- `res/color/selector_chip_bg.xml`（新）：分类/筛选芯片的选中底。
- `res/color/selector_nav.xml`：选中主色、未选中次级前景（不再是硬编码白色）。
- `res/color/selector_control_text.xml`（新）：芯片前景随选中切换。
- `res/drawable/shape_item.xml`（手机端设置/列表行）：圆角 4→14dp、内边距 8/12→11/14dp。
- `res/drawable/selector_control_pill.xml`（新）、`shape_control_scrim.xml`（新）、
  `shape_nav_bar.xml`（新）。
- 海报角标：`shape_vod_site` / `shape_vod_year` / `shape_vod_remark` 改为
  主色容器底 / 中性深底 / 胶囊深底，配对应前景色。

### 2.3 页面级改造（手机端为主）

| 页面 | 布局 | 改了什么 |
| --- | --- | --- |
| 首页外壳 | `mobile/layout/activity_home.xml` | 底部导航改为 64dp 上部圆角承托面，图标 22dp、标签开启、双态着色 |
| 首页内容 | `mobile/layout/fragment_vod.xml` | 品牌栏收紧（logo 30dp、标题 18sp 中粗）、分类 chips 行左右留白与滚动惯性、三个 FAB 改 14dp 方块并换主色容器底 |
| 影片卡片 | `mobile/layout/adapter_vod.xml` | 圆角 14dp、信息区改渐隐、片名左对齐中粗、集数行降级、站点角标移至顶部内缩、进度条 3dp |
| 紧凑卡片 | `mobile/layout/adapter_vod_rect.xml` | 同上视觉语言 |
| 分类芯片 | `mobile/layout/adapter_type.xml` | 胶囊、13sp、`selector_text` 双态 |
| 播放页 | `mobile/layout/view_control_vod.xml` | 控制层改上下渐隐 scrim、播放按钮 48→56dp、进度行左右对齐 |

以上布局**ID 与视图类型逐一保留**，`ViewBinding` 字段名与类型不变，业务代码零改动。

## 3. 保留约束（改 UI 时的红线）

1. **ID/视图类型不可变**：工程开启 `viewBinding = true`，布局 ID 即绑定字段名，视图类型决定字段类型。
   任何「更合理」的重命名或换控件都会让 `binding.xxx` 编译失败。
2. **主题自定义链路不可绕过**：所有可见色必须走 `?attr/color*` 或 `@color/webhtv_*`
   （`ThemeBinder` / `ThemeResolver` 会在运行时重写这些 token，硬编码色无法被用户主题覆盖）。
3. **token 门禁**：`python3 scripts/check_ui_tokens.py --strict` 五项检查必须通过，
   禁止在 layout / drawable / color 里写裸 hex（新增叠加色请登记进 token 表）。
4. **对比度**：token 表 38 组前景/背景配对全部 ≥ 4.5:1（`outline`/`focus` 按 3:1 非文本门槛）。
5. **壁纸系前景色**：手机上直接画在壁纸上的文字必须用 `?attr/webhtvColorOnWallpaper*`，
   不能用 `?attr/colorOnSurfaceVariant`（浅色表会解析成深灰，压在海报上只有 1.4:1）。

## 4. 验证结果

| 项目 | 结果 |
| --- | --- |
| XML 结构解析（`app/src/*/res/**/*.xml`） | 0 个解析失败 |
| ID / 视图类型比对（394 个布局） | **0 丢失、0 类型变化** |
| 本次新增资源引用可解析 | 全部命中（新增 attrs 均为 Material 库属性，改造前已在用） |
| `check_ui_tokens.py --strict` | 新增违规 **0**（剩余 4 项为仓库原有文件，本次未触碰） |
| 对比度门禁（38 组） | 0 失败，最低 4.27 |
| XML ↔ `ThemeTokens.java` 色槽同步 | 53/53 一致 |

### 未做与已知风险

- **未产出 APK**：本次改造环境没有 JDK 21 / Android SDK 37 / NDK 29，无法执行 Gradle 打包，
  因此编译级验证（AAPT2 资源链接、ViewBinding 生成、单测）尚未跑过。改动是按「只动资源表现属性、
  不动 ID/类型/结构」的最保守方式做的，但仍建议先跑一次
  `bash gradlew :app:assembleMobileArm64_v8aDebug`。
- **电视端（leanback）只改了全局层**：调色板、圆角、排印、`Control`、`selector_item` 会生效；
  leanback 的专属布局（`activity_setting*`、`activity_collect` 等）未逐页重排。
- **WebHome 自定义首页（HTML）未改**：那是用户自己的网页，样式不归 App 管。
- **详情页 hero 渐隐未落地**：`activity_tmdb_detail.xml`（1600+ 行）本次未改，hero 仍是整屏平铺遮罩。
  如需该效果，把 `backdropShade` 的 `android:background` 换成渐变 drawable 即可（一行改动）。
- 一处单测期望已按新语义更新：`ThemeResolverOverrideTest` 原先断言「遮罩文字恒等于 onPrimary」，
  现改为「遮罩文字在复合遮罩上 ≥4.5:1」——深色表 onPrimary 已变成深琥珀墨色，不能再当遮罩文字用。

## 5. 继续往下做的话

1. 起一个真机/模拟器把首页→详情→播放→设置走一遍，按截图微调间距（当前数值来自 4pt 梯，未实机校准）。
2. 电视端逐页重排：`activity_setting*` 行高与分组标题、`activity_collect` / `activity_history` 网格密度。
3. 图标体系：`ic_nav_*` 与 `ic_control_*` 仍是线性旧图标，可换成同一套 2dp 描边、24dp 网格的新图标集。
4. 动效：页面切换（共享元素转场）、卡片按压缩放、播放页控制层淡入淡出，目前都还是系统默认。
