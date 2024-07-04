defmodule Grades.CalculatorTest do
  use ExUnit.Case
  alias Grades.Calculator

  describe "percentage_grade/1" do
    test "calculates percentage grade when homework is empty" do
      assert Calculator.percentage_grade(%{
                 homework: [],
                 labs: [0.9, 0.8, 0.7],
                 midterm: 0.80,
                 final: 0.90
               }) == 77
    end

    test "calculates percentage grade when homework is not empty" do
      assert Calculator.percentage_grade(%{
                 homework: [0.9, 0.8, 0.7],
                 labs: [0.9, 0.8, 0.7],
                 midterm: 0.80,
                 final: 0.90
               }) == 81
    end
  end

  describe "letter_grade/1" do
    test "returns EIN when avg_homework is less than 0.4" do
      assert Calculator.letter_grade(%{
                 homework: [],
                 labs: [],
                 midterm: 0.80,
                 final: 0.90
               }) == "EIN"
    end

    test "returns A when mark is greater than 0.845" do
      assert Calculator.letter_grade(%{
                 homework: [0.85],
                 labs: [0.85],
                 midterm: 0.85,
                 final: 0.85
               }) == "A"
    end

    test "returns C when mark is greater than 0.595" do
      assert Calculator.letter_grade(%{
                 homework: [0.60],
                 labs: [0.60],
                 midterm: 0.60,
                 final: 0.60
               }) == "C"
    end

    test "returns E when mark is greater than 0.395" do
      assert Calculator.letter_grade(%{
                 homework: [0.40],
                 labs: [0.40],
                 midterm: 0.40,
                 final: 0.40
               }) == "E"
    end
  end

  describe "numeric_grade/1" do
    test "returns 0 when avg_homework is less than 0.4" do
      assert Calculator.numeric_grade(%{
                 homework: [],
                 labs: [],
                 midterm: 0.80,
                 final: 0.90
               }) == 0
    end

    test "returns 9 when mark is greater than 0.845" do
      assert Calculator.numeric_grade(%{
                 homework: [0.85],
                 labs: [0.85],
                 midterm: 0.85,
                 final: 0.85
               }) == 9
    end

    test "returns 4 when mark is greater than 0.595" do
      assert Calculator.numeric_grade(%{
                 homework: [0.60],
                 labs: [0.60],
                 midterm: 0.60,
                 final: 0.60
               }) == 4
    end

    test "returns 1 when mark is greater than 0.395" do
      assert Calculator.numeric_grade(%{
                 homework: [0.40],
                 labs: [0.40],
                 midterm: 0.40,
                 final: 0.40
               }) == 1
    end
  end
end
