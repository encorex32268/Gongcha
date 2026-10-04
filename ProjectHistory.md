-------
2026.10.04
commits: 
| SHA | Commit訊息 | 主要變更 |
| :--- | :--- | :--- |
| `a64bf6f` | feat: implement tea order shared transition, size selection, and top level nav graphs | 實作 `tea_order` 飲品放大共享元素轉場（`sharedElement`）、尺寸規格選擇元件（`SizeSelection` / `SectionCard`），並依 `TopLevelDestination` 拆分獨立 NavGraph（`MenuNavGraph`, `ShoppingCartNavGraph`, `HistoryNavGraph`, `MemberNavGraph`）。 |
| `e0c608f` | feat: add shopping cart destination, refactor menu package structure, and scaffold tea order | 增加購物車導航目的地與向量圖示，重構 menu 套件路徑結構，並搭建 `tea_order` MVI 架構骨架。 |
| `54d130d` | feat(app): implement elliptical bottom navigation bar and type-safe routing | 實作客製化橢圓懸浮 BottomBar、自訂指示器裁切與型別安全導航切換。 |
| `57eee73` | feat(menu): implement store selection screen and bind selected store state | 實作門市選擇頁面（分區排序列表）與已選門市狀態綁定。 |
| `defd453` | feat(core): implement selected store persistence and add navigation design resources | 透過 DataStore 持久化存儲最新選取門市 ID，新增導航設計系統圖示。 |
| `0da85fb` | feat(app): add member destination and wire navigation callbacks in NavRoot | 在 `NavRoot` 增加會員目的地與畫面跳轉回呼接線。 |
| `8d7803a` | feat(menu): add store selection warning animation, tea filtering, and member navigation | 增加未選門市點擊提示動畫、飲品種類篩選 Chips（原茶/奶香/特調/冰沙）與會員導航。 |
| `00fc6cf` | feat(core): implement remote stores fetching and specific version code synchronization | 串接 Firestore 遠端門市資料與版本號比對快取同步機制（Offline-first）。 |
-------
2026.10.03
commits: 
| SHA | Commit訊息 | 主要變更 |
| :--- | :--- | :--- |
| `d945be6` | feat(app): configure GongchaApp Koin initialization and Navigation root | 配置 Application 層 Koin 依賴注入模組初始化與 Navigation 導航入口 `NavRoot`。 |
| `c956138` | feat(menu): connect MenuViewModel with repository and update UI components | 串接 `MenuViewModel` 與 `GongchaRepository`，更新菜單門市與飲品卡片介面。 |
| `7cdaadc` | feat(core:data): implement offline-first repository and datastore | 實作 `GongchaRepositoryImpl` 離線優先資料流整合與 `AppDataStore` Preferences。 |
| `23f4264` | feat(core:database): setup Room database, entities, daos, and local data source | 建立 Room 資料庫、`TeaEntity` / `StoreEntity`、Dao 與 `LocalDataSource`。 |
| `11d307e` | feat(core:network): extract network module and implement FirebaseRemoteDataSource | 抽取 `core:network` 模組並透過 Firebase Firestore 實作 `RemoteDataSource`。 |
| `8338c83` | refactor(core:domain): reorganize models and define repository & datastore contracts | 重構 domain 模組，定義 `GongchaRepository` 與 `AppDataStore` 介面合約。 |
| `2c1e83a` | build: configure dependencies for navigation, datastore, and core:network | 配置 Navigation Compose、DataStore 與 `core:network` 相關依賴。 |
| `24cc98c` | Add Result & UiText | 在 `core:domain` 與 `core:presentation:ui` 建立通用 Result 封裝、DataError 與 UiText 工具。 |
| `3d091a2` | Push data to firebase firestore | 建立上傳門市與茶飲資料至 Firebase Firestore 的腳本與資料集合。 |
-------
2026.10.02
commits: 
| SHA | Commit訊息 | 主要變更 |
| :--- | :--- | :--- |
| `1d3522d` | feat(menu): implement MenuScreen, components, and preview data | 實作 `MenuScreen` 畫面、`TeaCard`、`StoreCard`、`TeaTypeChips` 與 Preview 假資料。 |
| `82da53b` | feat(domain): define Store and Tea domain models with mock data | 定義 `Store`、`Tea`、`BusinessHour` 純 Domain 模型與測試預覽資料。 |
| `139574d` | assets(designsystem): add Gong Cha drink drawable resources | 加入貢茶飲品圖片與向量圖示設計資源。 |
| `c702677` | feat(designsystem): setup theme, typography, icons, and base button components | 建立 `GongchaTheme`、`Color`、`Typography`、`Icons` 與 `PrimaryButton` 元件。 |
| `e6cc718` | Refactor core:database | 重構 `core:database` 模組 Gradle 設定與套件路徑。 |
| `963a4b2` | Refactor All feature's core's presentation module. | 重構所有功能模組（auth, cart, history, menu）的 presentation 模組設定。 |
| `6e85bbf` | Refactor All feature's core's data module. | 重構所有功能模組（auth, cart, history, menu）的 data 模組設定。 |
| `1ba5afe` | Refactor All feature's core's domain module. | 重構所有功能模組（auth, cart, history, menu）的 domain 模組設定。 |
| `832baee` | Add Room , Ktor , Jvm library Plugins | 在 `build-logic` 建立 Room、Ktor 與 JVM 函式庫 Convention Plugins。 |
| `cfe38e6` | Add AndroidLibrary FeatureUI Convention Plugin | 建立 `AndroidFeatureUiConventionPlugin` 統整 UI 模組依賴。 |
| `3688ccd` | Add AndroidLibrary Plugin | 建立 `AndroidLibraryConventionPlugin` 與 Compose 擴充外掛。 |
| `af3cf75` | AndroidApplication ConventionPlugin | 建立 `AndroidApplicationConventionPlugin` 與 ApplicationCompose 外掛。 |
| `8b8df36` | Add Dependenices | 在 `gradle/libs.versions.toml` 完善 Koin、Compose BOM 等版本目錄定義。 |
| `3996dc0` | Create App Modules | 建立多模組架構（core, auth, cart, history, menu 之 domain/data/presentation）。 |
| `09b2e2b` | init commit | 專案初始化提交（Gradle 8/9 配置、基本 Android 目錄結構）。 |
-------
