# PinnedTabs

![Build](https://github.com/kristensala/pinned-tabs/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/26713-pinnedtabs)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/26713-pinnedtabs)

<!-- Plugin description -->
This plugin is inspired by [Harpoon](https://github.com/ThePrimeagen/harpoon). Intellij does provide something similar out of the box called Switcher.
But it has a major flaw, files do not keep their order, which bothers me.
<!-- Plugin description end -->

# Known bugs
- [x] On file open it does not give focus when using IdeaVim
- [ ] If you have previously mapped J or K then this plugin will not override those mappings
- [x] Getting java.lang.IndexOutOfBoundsException: bitIndex < 0: -2 error sometimes when setting JList selectedIndex
- [x] In `.pinned_tabs` file paths are sometimes added on top of eachother, rather than on each line separately
- [ ] Does not create a `.pinned_file` under `./idea` folder

# QOL updates
- [ ] When opening the popup, highlight currently open file, not the first one. Currently selected index is always by default 0


## Usage
### Pinning a file
Pin file action id: `com.github.kristensala.pinnedtabs.popup.PinFileAction` 

### Removing a pin
When the Pinned Files popup is open, select a file you want to remove and press `d`

### Navigating between pinned files
When the popup is open you can move in the list using arrow keys or `j` and `k`. Currently `j` and `k` are hardcoded and can not be remapped, meaning
if you have `j` and `k` mapped to something else in the editor already, it might cause some mishaps to happen.

### Changing the order of pinned files
There is no intuitive way to change the order of the files at the moment. The best way is to
go to the `.pinned_tabs` file under `.idea/` directory and change the order of the paths there.
- the fastest way to get to `.pinned_files` is to open the Pinned Files popup and when open press `s`

In the future, I might add some shortcuts.

### Fixing possible errors
@todo

### ideaVim setup

```
nmap <C-e> <Action>(com.github.kristensala.pinnedtabs.popup.TogglePopupAction)
nmap <leader>m <Action>(com.github.kristensala.pinnedtabs.popup.PinFileAction)
```

## How it works

Each project using any JetBrains IDE will generate a `.idea/` directory.
This plugin will create a `.pinned_tabs` file under said directory
where each line is a path to a pinned file.

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation
