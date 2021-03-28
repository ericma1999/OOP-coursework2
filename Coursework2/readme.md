# How to search
To search on multiple columns please click on each of the column header and a dialog box will pop up.
The search is inclusive ie. it would apply all search query you have input on each of the column

# For Graham
Regarding the bug for the flickering chart. I managed to make a work around for this coursework for now.
I had to set the layout of the chart to use GridLayout, as in this case i always want it to be at the bottom.
However, this limits the placement of the legend if another user wants to be position it to the left or to the right.
The sidepanel will show the current filters you have on

#### Question regarding MVC with swing
Java Swing is already kind of a controller, so I wasn't sure how to structure the application.
As in generally in an mvc application you would create a controller and pass in the view and the model.
And inside controller it handles the model action and calls the view method to update the view. But in this case, since
the swing UI is already kind of a controller so I opted to have the view just call the controller and the view updating itself.

Another way to adhere strictly to mvc pattern is to set all the button listeners for the view inside the controller.

Could you clarify both approaches and which is better in this case?

# Things to do if have time

- Make charts generate better colours using colour theory instead of just random</li>
- Add limit to showing per page, eg 50 results
- Improve error message instead of just generic error message

# Change log from merged branch

+ changed from custom patient type back to normal list
+ removed interfaces, no time to make it clean
+ "fixed" flickering issue for charts
+ changed to use single class imports
