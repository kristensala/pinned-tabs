# PinnedTabs

![Build](https://github.com/kristensala/pinned-tabs/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/MARKETPLACE_ID.svg)](https://plugins.jetbrains.com/plugin/MARKETPLACE_ID)

<!-- Plugin description -->
This plugin is inspired by [Harpoon](https://github.com/ThePrimeagen/harpoon). Intellij does provide something similar out of the box called Switcher.
But it has a major flaw, files do not keep their order, which bothers me.
<!-- Plugin description end -->

# Known bugs
- [x] On file open it does not give focus when using IdeaVim
- [ ] If you have previously mapped J or K then this plugin will not override those mappings
- [ ] Getting java.lang.IndexOutOfBoundsException: bitIndex < 0: -2 error sometimes when setting JList selectedIndex
- [ ] In `.pinned_tabs` file paths are sometimes added on top of eachother, rather than on each line separately
- [ ] Does not create a `.pinned_file` under `./idea` folder

## Usage
### Pinning a file

### Removing a pin

### Fixing possible errors

### Shortcuts when using ideaVim

```
nmap <C-e> <Action>(com.github.kristensala.pinnedtabs.popup.TogglePopupAction)
nmap <leader>m <Action>(com.github.kristensala.pinnedtabs.popup.PinFileAction)
```

## How it works

Each project using any JetBrains IDE will generate a .idea/ directory.
This plugin will create a `.pinned_tabs` file under said directory
where each line is a path to a pinned file.

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation
