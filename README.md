# click-bot

A simple Java application for creating click macros.

## Setup

This project was written in Eclipse and can be imported easily as an Eclipse
Java Project. Otherwise, any other method of compiling Java programs will do.

## Features

The application currently supports four command categories: `Click`, `Move`,
`Type`, and `Wait`. Each can be added using the Add Command button or by
entering into the command text field.

### Click

The command line usage is:
* `[click/mdown/mup]`
* `[click/mdown/mup] [l|r|m]`
* `[click/mdown/up] [x-pos] [y-pos]`
* `[click/mdown/up] [x-pos] [y-pos] [l|r|m]`

`[click/mdown/mup]` specifies whether the action is a click, mouse down, or
mouse up. `[l|r|m]` refers to any combination of the left, right, or middle
keys. If omitted, the left button is used. `[x-pos]` and `[y-pos]` refer to the
click location. If omitted, the mouse will click at its current location
(during execution).

The GUI provides the same functionality, but has one additional button which
allows the user to point to a location. The button should be clicked once;
then, the user can move the mouse and press the space bar to confirm the
location.

### Move

The command line usage is:
* `move [x-pos] [y-pos]` - move the mouse to the specified location.

The GUI provides the same pointing functionality as with Click.

### Type

The command line usage is:
* `[type/kdown/kup] [key]`
* `type "[string]"`

`[type/kdown/kup]` specifies the action (type, press, or release), and `[key]`
specifies the key to type. Note that this refers to a physical key, so for
example, `a` and `A` both map to the same key. The second usage allows for
typing text, in which case and other characters requiring the Shift key are
preserved. The available keys are:
* `abcdefghijklmnopqrstuvwxyz`
* `ABCDEFGHIJKLMNOPQRSTUVWXYZ`
* `0123456789-=`
* `!@#$%^&*()_+`
* `,./;'[]\`
* `<>?:"{}|`
* `alt, backspace, capslock, ctrl, delete, down, end, enter, esc,`  
  `f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, home, ins,`  
  `kp_down, kp_left, kp_right, kp_up, left, numlock, pgdn, pgup,`  
  `right, scrolllock, shift, space, tab, up`
  
### Wait

The command line usage is:
* `wait [time]` - wait the specified amount of time (in milliseconds)
  
## Future Ideas/TODOs

* File menu options (new, open, save, save as)
* Better help/about dialog
* Record functionality for adding keys
* Kill switch
* Optional looping
* Status bar (running status, current mouse location, etc.)
* If-Then-Else logic controls
* Fully command-line interface (in addition to GUI)
* Make UI pretty cross-OS and cross-resolutions
