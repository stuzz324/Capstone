package com.magnus_veterans_project.io

import android.content.Context
import kotlin.io.bufferedReader

/* Functions in this kotlin file accomplish file input and output operations on field data. As of
 * writing this (2/20/2026), it only contains three functions:
 *
 *      strOut(context: Context, filename: String, data: String): Unit
 *      strIn(context: Context, filename: String): String
 *      clear(context: Context, filename: String): Unit
 *
 * Note: in kotlin, 'Unit' is the equivalent of 'void' and therefore is implicit in the absence of
 *       an explicitly stated return value.
 *
 */

//strOut
/* Basic out to file function that uses Android's 'Context.openFileOutput' function. This is a
 * slightly modified version of an example on Android Studio's website. It will be expanded on for
 * our purposes, though.
 *
 * A few things to note:
 *  - If the specified file doesn't exist, 'openFileOutput' will create a new one.
 *  - It is important that 'Context.MODE_PRIVATE' gets passed to the 'mode' parameter, as it ensures
 *    that ONLY this application has access to it (and others if specified with appropriate
 *    permissions).
 *  - The '.use' inline function executes the block of code after it for the output stream and
 *    closes it safely.
 */
fun strOut(context: Context, filename: String, data: String) {
    context.openFileOutput(filename, Context.MODE_PRIVATE).use {
        it.write(data.toByteArray()) //UTF-8 encoding using toByteArray()
    }
}

//strIn
/* Basic file reader using Android's 'Context.openFileReader' and kotlin standard 'bufferedReader()'
 * to read all lines from the desired text file and join them to a string. Like strOut, this is a
 * slightly modified version of the example function on Android Studio's website. We will be
 * modifying this more in the future.
 */
fun strIn(context: Context, filename: String): String {
    return context.openFileInput(filename).bufferedReader().useLines { lines ->
        lines.joinToString("\n")
    }
}

//clear
/* A function that uses strOut to overwrite the contents of a text file. Practically, it has the
 * same body as strOut. I believe this will reduce execution time when called, as fewer functions
 * must be called.
 *
 */
fun clear(context: Context, filename: String) {
    context.openFileOutput(filename, Context.MODE_PRIVATE).use {
        it.write(("").toByteArray())
    }
}