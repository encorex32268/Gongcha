-------
2026.10.07
commits: 
| SHA | Commit訊息 | 主要變更 |
| :--- | :--- | :--- |
| `0b6aeeb` | refactor(app): inject CartRepository in MainViewModel | 重構 `MainViewModel` 改為注入專屬的 `CartRepository` 監聽購物車項目數量。 |
| `2d0765d` | feat(auth): implement phone number login screen and validator with unit tests | 實作手機號碼登入頁面 `LoginScreen`、ViewModel 與 `PhoneNumberValidator` 驗證邏輯及單元測試。 |
| `786673b` | feat(member): update MemberScreen and MemberCard with membership level badges | 更新 `MemberScreen` 與 `MemberCard` 支援無等級/銅/銀/金級漸層卡面、條碼視覺與點數展示。 |
| `794fd04` | feat(history): implement history order list screen, status chip, and viewmodel | 實作歷史訂單列表頁面 `HistoryScreen`、訂單卡片 `HistoryItemCard`、狀態標籤 `StatusChip` 與 MVI 架構。 |
| `e577ffd` | feat(cart): add checkout event flow and update CartViewModel with CartRepository | 擴充購物車結帳事件流程（`CartEvent.OnCheckoutSuccess`）並重構 `CartViewModel` 改用 `CartRepository`。 |
| `7ea6e63` | feat(menu): create MenuRepository in domain and data modules and decouple from core | 於 `menu:domain` 與 `menu:data` 模組建立專屬 `MenuRepository` 與實作，從 core 模組徹底解耦菜單與門市存取。 |
| `9e79786` | refactor(core): decouple GongchaRepository into CartRepository and define domain models | 解耦通用 Repository 為專責之 `CartRepository`，新增 `History`、`User`、`MemberLevel` 與 `OrderStatus` 等 Domain 模型與 DTO。 |
| `d155c2e` | refactor(ui): reorganize tea order models, add member level badges, and NoticeCard | 整理飲品客製化模型至 `tea_order` 套件，將會員等級徽章資源移至 `core:presentation:ui`，並抽取通用 `NoticeCard`。 |
| `f66f635` | refactor(designsystem): remove legacy drink drawables and add contactless icon | 清理不再使用的飲品舊圖檔資源，並新增感應付款圖示 `contactless.xml` 與晶片元件樣式。 |
-------
2026.10.06
commits: 
| SHA | Commit訊息 | 主要變更 |
| :--- | :--- | :--- |
| `c1361f4` | feat(member): initialize member modules and scaffold member presentation | 建立 `member` 模組（`domain`, `data`, `presentation`）與配置 Convention Plugins，搭建 `MemberScreen`、`MemberCard`（銅/銀/金級會員卡面）與 MVI 架構骨架。 |
| `71b32d1` | assets(app): update app launcher icons and app name to Koalacha | 更新應用程式啟動圖示（Adaptive icon 與各解析度 webp）及應用程式名稱為 Koalacha。 |
| `6f26e2e` | feat(app): connect cart screen, add bottom bar badge counter, and wire cart navigation | 在 `NavRoot` 串接購物車畫面、`MainViewModel` 監聽購物車項目數量並在 `AppBottomBar` 顯示 Badge 徽章，接線跳轉購物車導航。 |
| `041e346` | feat(auth): scaffold intro screen and viewmodel | 搭建會員認證引導頁面 `IntroScreen` 與其 MVI 狀態架構骨架。 |
| `c6803e0` | feat(cart): implement shopping cart presentation with item list and empty state | 實作購物車畫面 `CartScreen`、空購物車提示 `CartEmptyView`、商品卡片 `CartItemCard` 與 `CartViewModel`。 |
| `8fb818c` | feat(menu): connect tea order customization and add to cart flow | 串接客製化飲品點餐（冰塊、甜度、配料、尺寸）與加入購物車事件流程，並監聽門市變更。 |
| `ca98a30` | feat(core): implement CartItem model, database migrations, and repository operations | 建立 `CartItem` 領域模型、Room 資料表與 Migration（1->2, 2->3）、DAO 與 Repository 購物車操作合約。 |
| `f7f27fe` | refactor(ui): extract order options to shared presentation models and add Counter | 將甜度、冰塊、配料、尺寸等選項列舉抽取至 `core:presentation:ui` 共享模組，並新增通用 `Counter` 元件。 |
| `80ff242` | feat(designsystem): update color palette to artisanal theme and add delete icon | 更新 Artisanal Washi & Tea 主題配色與圓角形狀、移除自訂字型改用預設 SansSerif，並新增垃圾桶刪除圖示與按鈕狀態支援。 |
-------
2026.10.05
commits: 
| SHA | Commit訊息 | 主要變更 |
| :--- | :--- | :--- |
| `cf46517` | feat(menu): implement tea order customization options for size, sweetness, ice, and toppings | 實作飲品客製化選擇區塊（杯型大小、甜度、冰量、配料多選）與底部訂購結帳列 `OrderBottomBar`。 |
| `2c97954` | refactor(app): wrap bottom bar inside SharedTransitionLayout in NavRoot | 在 `NavRoot` 將 `AppBottomBar` 移入 `SharedTransitionLayout` 內部統一階層佈局。 |
| `0e1c721` | feat(designsystem): add plus, minus, check icons and shape parameter to PrimaryButton | 新增加號、減號與打勾向量圖示資源，並擴充 `PrimaryButton` 支援自訂形狀（`Shape`）。 |
| `7c62f50` | refactor(core): streamline GongchaRepository and introduce OfflineFirstGongchaRepository | 重構 Repository 合約與 `LocalDataSource` 介面，引入 `OfflineFirstCartRepository`，並同步更新 `MenuViewModel` 與 `StoreViewModel`。 |
| `7ef8e07` | build: add androidTest dependencies to data and presentation modules | 為 `auth:data`、`core:data` 與 `core:presentation:ui` 模組配置 `androidx.junit` Android 測試依賴，並更新 JDK 設定。 |
| `754bd38` | docs: add ProjectHistory.md tracking commit history | 建立 `ProjectHistory.md` 紀錄專案歷程與 Commit 規範。 |
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
| `c956138` | feat(menu): connect MenuViewModel with repository and update UI components | 串接 `MenuViewModel` 與 `CartRepository`，更新菜單門市與飲品卡片介面。 |
| `7cdaadc` | feat(core:data): implement offline-first repository and datastore | 實作 `OfflineFirstCartRepository` 離線優先資料流整合與 `AppDataStore` Preferences。 |
| `23f4264` | feat(core:database): setup Room database, entities, daos, and local data source | 建立 Room 資料庫、`TeaEntity` / `StoreEntity`、Dao 與 `LocalDataSource`。 |
| `11d307e` | feat(core:network): extract network module and implement FirebaseRemoteDataSource | 抽取 `core:network` 模組並透過 Firebase Firestore 實作 `RemoteDataSource`。 |
| `8338c83` | refactor(core:domain): reorganize models and define repository & datastore contracts | 重構 domain 模組，定義 `CartRepository` 與 `AppDataStore` 介面合約。 |
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
