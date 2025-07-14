# MapLocker – Map & Template Locking Plugin

**MapLocker** lets players lock maps and templates by crafting them in a honeycomb frame — place your map or template in the center surrounded by 8 honeycombs to create an uncopyable item. Blocks duplication in Crafting Tables, Cartography Tables, and Auto-Crafters.

**Join our** [**Discord**](https://discord.gg/YGzA4UxzFB) — discover other cool plugins, suggest new features, or get help with configurations.

### Features ✨

- **Honeycomb Locking Recipe**  
  Craft a locked map or template by surrounding it with 8 honeycombs.

- **Duplication Prevention**  
  Prevent map/template copying in Crafting Tables, Cartography Tables, and Auto-Crafters.

- **Template & Map Control**  
  Toggle locking for maps and/or templates independently.

- **Customizable Messages**  
  MiniMessage support for lock notifications and reload feedback.

- **Hot Reload**  
  Reload config and recipes on-the-fly with `/maplocker reload` and `/maplocker reloadrecipes`.

### Main config (`config.yml`) ⚙️

<details>
  <summary><strong>config.yml</strong></summary>

  ```yaml
# Join our discord - https://discord.gg/YGzA4UxzFB you can find other cool plugins there.
# Permissions
# "mapLocker.reload" - permission required to use "/maplocker reload" command

# if true, players can lock their maps
doLockMaps: true

# if true, players can lock their templates
doLockTemplates: true

# supports MiniMessage
messages:
  reload:
    usage: "<red>✘ <white>Usage: :\"/maplocker reload\" or \"/maplocker reloadrecipes\""
    noPermissionToReload: "<red>✘ <white>You don't have permission to reload Config!"
    recipesReloaded: "<green>✔ <white>Recipes reloaded!"
    configReloaded: "<green>✔ <white>Config reloaded!"
  items:
    # after you changed this, run "/maplocker reloadrecipes"
    locked: "<blue><bold>i <reset><white>Item is locked and can't be copied."
  onCopyTry:
    template: "<red>✘ <white>This template is locked and can't be copied."
    map: "<red>✘ <white>This map is locked and can't be copied."
````

</details>

### Permissions 🔐

| Permission Node           | Description                          |
| ------------------------- | ------------------------------------ |
| `mapLocker.reload`        | Allows reloading the plugin config   |
| `mapLocker.reloadrecipes` | Allows reloading the locking recipes |

### Commands 📟

| Command                    | Description                   |
| -------------------------- | ----------------------------- |
| `/maplocker reload`        | Reload plugin configuration   |
| `/maplocker reloadrecipes` | Reload honeycomb lock recipes |
