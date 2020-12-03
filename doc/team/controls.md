# Controls!

Controls is a kind language for making control maps! It is designed to be okay with pretty much whatever you put in.

Each Controls statement represents a single control-- whether that's "movement", "grabber", "intake", or 
whatever. To use Controls, just make a class that inherits from ControlMap and has a bunch of static Strings.
For example,
```java
public class BasicDrivingControlMap extends ControlMap {
    public static String drive = "vector3(leftStickX, leftStickY, rightStickX)";
}
```

## Table of Contents

- [Controls!](#controls-)
  * [More Detail](#more-detail)
    + [Basic Syntax](#basic-syntax)
    + [Data Types](#data-types)
    + [Function Details](#function-details)
      - [Input Methods](#input-methods)
        * [DPAD_LEFT](#dpad-left)
        * [DPAD_RIGHT](#dpad-right)
        * [DPAD_DOWN](#dpad-down)
        * [DPAD_UP](#dpad-up)
        * [A](#a)
        * [B](#b)
        * [X](#x)
        * [Y](#y)
        * [LEFT_BUMPER](#left-bumper)
        * [RIGHT_BUMPER](#right-bumper)
        * [LEFT_TRIGGER](#left-trigger)
        * [RIGHT_TRIGGER](#right-trigger)
        * [LEFT_STICK_X](#left-stick-x)
        * [LEFT_STICK_Y](#left-stick-y)
        * [RIGHT_STICK_X](#right-stick-x)
        * [RIGHT_STICK_Y](#right-stick-y)
      - [Output Types](#output-types)
        * [SCALAR](#scalar)
        * [VECTOR2](#vector2)
        * [VECTOR3](#vector3)
        * [VECTOR4](#vector4)
        * [TOGGLE](#toggle)
        * [HOLD](#hold)
        * [PUSH](#push)
        * [COMBO](#combo)
        * [TOGGLE_BETWEEN](#toggle-between)
      - [Middleware](#middleware)
        * [NOT](#not)
        * [AND](#and)
        * [OR](#or)
        * [TERNARY](#ternary)
        * [IF](#if)
        * [SCALE](#scale)
        * [DUMMY](#dummy)
        * [NULL](#null)
        * [LITERAL](#literal)
        * [SET_VARIABLE & GET_VARIABLE](#set-variable---get-variable)
        * [GREATER_THAN](#greater-than)
        * [LESS_THAN](#less-than)

## More Detail

### Basic Syntax

Every Controls statement is one function, maybe with other functions as arguments. The only thing Controls cares about is word
separations-- `vector3<scale<leftStickX 0.5> leftStickY rightStickY>` is just as valid as `vector3(scale(leftStickX(), 0.5), leftStickY(), rightStickY());` or 
`VECTOR3 SCALE LEFT_STICK_X 0.5 LEFT_STICK_Y RIGHT_STICK_Y` The only thing you really have to worry about is making sure that the statement is *one* function.
`scale(leftStickX, 0.5), leftStickY, rightStickY` is 3 functions, so it is __invalid__.  

Every function has a given argument count. You can see detailed info for all functions in [Function Details](#function-details).

### Data Types

Controls has 3 basic data types, all of which are automatically coerced/casted/converted into whatever value is needed.

* **Vector**  
  Vectors are float arrays. They are converted into floats by taking the first element (`vector[0]`).
* **Scalar**  
  Scalars are floats. They are converted into vectors with `[scalar]` and booleans with `scalar != 0.0f`.
* **Booleans**  
  Booleans are... booleans. They are converted into scalars by using `bool ? 1f : 0f`-- if true, 1; otherwise, 0.
  
By using these conversions, any type can be converted into any other. 

### Function Details

#### Input Methods

Input Methods are represented as functions, but behave more like variables; they have 0 arguments, and you can leave off any parentheses at the end. They relate to a single part of the gamepad.

##### DPAD_LEFT

Directional pad left. Boolean, 0 arguments.

```jshelllanguage
dpadLeft
```

##### DPAD_RIGHT

Directional pad right. Boolean, 0 arguments.

```jshelllanguage
dpadRight
```

##### DPAD_DOWN

Directional pad down. Boolean, 0 arguments.

```jshelllanguage
dpadDown
```

##### DPAD_UP

Directional pad up. Boolean, 0 arguments.

```jshelllanguage
dpadUp
```

##### A

A button. Boolean, 0 arguments.

```jshelllanguage
a
```

##### B

B button. Boolean, 0 arguments.

```jshelllanguage
b
```

##### X

X button. Boolean, 0 arguments.

```jshelllanguage
x
```

##### Y

Y button. Boolean, 0 arguments.

```jshelllanguage
y
```

##### LEFT_BUMPER

Left bumper. Boolean, 0 arguments.

```jshelllanguage
leftBumper
```

##### RIGHT_BUMPER

Right bumper. Boolean, 0 arguments.

```jshelllanguage
rightBumper
```

##### LEFT_TRIGGER

Left trigger. Returns scalar, 0 arguments.

```jshelllanguage
leftBumper
```

##### RIGHT_TRIGGER

Right trigger. Returns scalar, 0 arguments.

```jshelllanguage
rightBumper
```

##### LEFT_STICK_X

Left stick horizontal axis. Returns scalar, 0 arguments.

```jshelllanguage
leftStickX
```

##### LEFT_STICK_Y

Left stick vertical axis. Returns scalar, 0 arguments.

```jshelllanguage
leftStickY
```

##### RIGHT_STICK_X

Right stick horizontal axis. Returns scalar, 0 arguments.

```jshelllanguage
rightStickX
```

##### RIGHT_STICK_Y

Right stick vertical axis. Returns scalar, 0 arguments.

```jshelllanguage
rightStickY
```

#### Output Types

Output Types can either be a cast/coercion/conversion, converting one data type to another, or a logical check. 

##### SCALAR

Converts a value to scalar. Returns scalar, 1 argument.

```jshelllanguage
scalar(leftBumper)
```

##### VECTOR2 

Converts two values to scalars, and then combines them to a 2-dimensional vector. Returns vector, 2 arguments.

```jshelllanguage
vector2(rightTrigger, leftTrigger)
```

##### VECTOR3 

Converts three values to scalars, and then combines them to a 3-dimensional vector. Returns vector, 3 arguments.

```jshelllanguage
vector3(scale(leftStickX(), 0.5), leftStickY(), rightStickY())
```

 ##### VECTOR4 
 
 Converts four values to scalars, and then combines them to a 4-dimensional vector. Returns vector, 4 arguments.
 
 ```jshelllanguage
vector4(dpadLeft, dpadTop, dpadRight, dpadBottom)
```

##### TOGGLE

Toggles on/off when its argument (as a boolean) changes from false to true. Returns boolean, 1 argument.

 ```jshelllanguage
toggle(a)
```

##### HOLD

Returns true when its argument (as a boolean) is true (i.e. a button is being held down). Returns boolean, 1 argument.

 ```jshelllanguage
hold(a)
```

##### PUSH

Returns true for one frame when its argument (as a boolean) switches from false to true. Returns boolean, 1 argument.

 ```jshelllanguage
push(rightBumper)
```

##### COMBO

Returns its second argument when its first argument is true. Otherwise, returns its first argument. Returns vector, 2 arguments.

```jshelllanguage
combo(a, rightTrigger)
```

##### TOGGLE_BETWEEN

Toggles between returning the 2nd argument and 3rd argument when the 1st argument changes from false to true. Returns vector, 3 arguments.

```jshelllanguage
toggleBetween(a, rightTrigger, leftTrigger)
```

#### Middleware

Middleware is the "Everything Else". It has logic, literals, variables, math, etc.

##### NOT

Logical NOT operator. Inverts a boolean. Returns boolean, 1 argument.

```jshelllanguage
not(a)
```

##### AND

Logical AND operator. Returns true if both argument 1 and argument 2 are true. Returns boolean, 2 argument.

```jshelllanguage
and(a, b)
```

##### OR

Logical OR operator. Returns whether either argument 1 or argument 2 is true. Returns boolean, 2 argument.

```jshelllanguage
or(a, b)
```

##### TERNARY

The same as [IF](#if), for if you feel like being fancy or can't use `if` because of reserved keywords. Returns vector, 3 arguments.

```jshelllanguage
ternary(a, rightTrigger, 0.5)
```

##### IF

If first child is false, returns 2nd child. Otherwise, returns 3rd child. Returns vector, 3 arguments.

```jshelllanguage
IF(a, rightTrigger, 0.5)
```

##### SCALE 

Multiplies each dimension of the 1st argument by the 2nd argument (as a scalar). Returns vector, 2 arguments

```jshelllanguage
scale(vector3(leftStickX, leftStickY, rightStickX), rightTrigger)
```   

##### DUMMY

Simply returns whatever its argument is. Intended for making Controls more readable. Returns vector, 1 argument

```jshelllanguage
dummy(push(a))
```

##### NULL

Returns 0. Returns scalar, 0 arguments.

```jshelllanguage
scale(rightTrigger, ternary(a, null, 1))
```

##### LITERAL

Used internally to represent scalar values (like `0.3`). Returns scalar, 1 argument.

```jshelllanguage
0.5
```

##### SET_VARIABLE & GET_VARIABLE

One of the only exceptions to the scalar/vector/boolean rule! Because of this, it's hard to give it an actual argument count. The setVariable and getVariable functions 
must have a 1-word *string* argument indicating the variable name to get/set. You can use quotes if you like, but they are not mandatory.

Variables are shared among all controls in a control model-- in the examples below, the `exampleVar`  is the same in both. However, they are not shared among all control models.

Variables are represented as vectors, but will be automatically converted to whatever they're being used as. A `setVariable` call will return the new value of the variable.

Whenever a Control with a `getVariable` function is called, then all Controls with `setVariable` functions for the variable will be automatically called. This is not recursive 
(a getter inside a setter will not result in an infinite loop).

```jshelllanguage
scale(leftStickX, getVariable(exampleVar))
``` 

```jshelllanguage
setVariable("exampleVar", ternary(a, 0.5, 1))
```

##### GREATER_THAN

Returns whether the first argument is greater than the second argument. Returns boolean, 2 arguments.

```jshelllanguage
greaterThan(rightTrigger, 0.5)
```

##### LESS_THAN

Returns whether the first argument is less than the second argument. Returns boolean, 2 arguments.

```jshelllanguage
lessThan(rightStickY, 0)
```



